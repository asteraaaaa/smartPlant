package com.csc3700.smartplant.service;

import com.csc3700.smartplant.model.Plant;
import com.csc3700.smartplant.repository.PlantRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** Runs the pump for a plant and records the result (lastWatered + system log). */
@Service
public class WateringService {
    private final PumpClientService pump;
    private final PlantRepository plantRepo;
    private final SystemLogService logService;
    private final ApplicationEventPublisher events;
    private final long defaultDurationMs;
    private final Duration cooldown;
    private final Map<Long, Instant> lastAutoWatering = new ConcurrentHashMap<>();

    public WateringService(PumpClientService pump, PlantRepository plantRepo, SystemLogService logService, ApplicationEventPublisher events,
                           @Value("${smartplant.pump.duration-ms}") long defaultDurationMs,
                           @Value("${smartplant.pump.cooldown-seconds}") long cooldownSeconds) {
        this.pump = pump;
        this.plantRepo = plantRepo;
        this.logService = logService;
        this.events = events;
        this.defaultDurationMs = defaultDurationMs;
        this.cooldown = Duration.ofSeconds(cooldownSeconds);
    }

    /** Manual / scheduled watering. Returns true if the pump ran. */
    @Transactional
    public boolean water(Plant plant, Long durationMs, String reason) {
        long ms = (durationMs == null || durationMs <= 0) ? defaultDurationMs : durationMs;
        boolean ok = pump.turnOnPump(ms);
        if (ok) {
            plant.setLastWatered(LocalDate.now());
            plantRepo.save(plant);
            logService.log("PUMP", "Watered " + plant.getName() + " for " + ms + " ms (" + reason + ")");
            events.publishEvent(new PlantWateredEvent(plant.getId()));
        } else {
            logService.log("WARNING", "Pump unreachable; could not water " + plant.getName() + " (" + reason + ")");
        }
        return ok;
    }

    /** Automatic watering from a sensor reading; skipped during the cooldown window. */
    @Transactional
    public void autoWater(Plant plant, float moisture) {
        Instant now = Instant.now();
        Instant last = lastAutoWatering.get(plant.getId());
        if (last != null && Duration.between(last, now).compareTo(cooldown) < 0) {
            return;
        }
        lastAutoWatering.put(plant.getId(), now);
        water(plant, null, "auto: moisture " + moisture + "% below minimum " + plant.getMinOptimalMoisture() + "%");
    }
}
