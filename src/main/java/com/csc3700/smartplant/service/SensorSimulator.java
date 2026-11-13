package com.csc3700.smartplant.service;

import com.csc3700.smartplant.dto.SensorDto;
import com.csc3700.smartplant.model.Plant;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Demo helper: posts fake readings through the normal ingest path, so thresholds,
 * auto-watering, cooldown and logging all behave as with a real sensor.
 * Soil dries out over time and recovers when the (mock) pump waters the plant.
 */
@Component
@ConditionalOnProperty(name = "smartplant.simulator.enabled", havingValue = "true")
public class SensorSimulator {
    private final PlantService plantService;
    private final SensorService sensorService;
    private final Random rnd = new Random();
    private final Map<Long, Float> moisture = new ConcurrentHashMap<>();

    public SensorSimulator(PlantService plantService, SensorService sensorService) {
        this.plantService = plantService;
        this.sensorService = sensorService;
    }

    @Scheduled(initialDelayString = "${smartplant.simulator.interval-ms}",
               fixedRateString = "${smartplant.simulator.interval-ms}")
    public void tick() {
        for (Plant p : plantService.getAll()) {
            float mid = (p.getMinOptimalMoisture() + p.getMaxOptimalMoisture()) / 2;
            float m = moisture.getOrDefault(p.getId(), mid);
            // dries 0.2-0.8 points per tick: over the 5 min auto-watering cooldown (30 ticks at 10s)
            // that is ~15 points, so one watering (+30) keeps the plant near its optimal range
            m = clamp(m - (0.2f + rnd.nextFloat() * 0.6f), 0, 100);
            moisture.put(p.getId(), m);

            SensorDto dto = new SensorDto();
            dto.setPlantId(p.getId());
            dto.setSoilMoisture(round(m));
            dto.setTemperature(round(mid(p.getMinOptimalTemperature(), p.getMaxOptimalTemperature()) + (rnd.nextFloat() - 0.5f) * 4f));
            dto.setHumidity(round(clamp(mid(p.getMinOptimalHumidity(), p.getMaxOptimalHumidity()) + (rnd.nextFloat() - 0.5f) * 10f, 0, 100)));
            sensorService.ingest(dto);
        }
    }

    /** The mock pump "watered" the plant: soil gets wetter. */
    @EventListener
    public void onWatered(PlantWateredEvent e) {
        moisture.computeIfPresent(e.plantId(), (id, m) -> clamp(m + 30f, 0, 100));
    }

    private static float mid(float a, float b) { return (a + b) / 2; }
    private static float clamp(float v, float lo, float hi) { return Math.max(lo, Math.min(hi, v)); }
    private static float round(float v) { return Math.round(v * 10f) / 10f; }
}
