package com.csc3700.smartplant.controller;

import com.csc3700.smartplant.model.Plant;
import com.csc3700.smartplant.model.Sensor;
import com.csc3700.smartplant.service.PlantService;
import com.csc3700.smartplant.service.SensorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sensor")
public class SensorController {
    private static final int MAX_TABLE_ROWS = 25;

    private final SensorService sensorService;
    private final PlantService plantService;

    public SensorController(SensorService sensorService, PlantService plantService) {
        this.sensorService = sensorService;
        this.plantService = plantService;
    }

    @GetMapping
    public String listAllSensor(@RequestParam(required = false) Long plantId,
                                @RequestParam(defaultValue = "24h") String range,
                                Model model) {
        List<Plant> plants = plantService.getAll();
        Plant selected = plants.stream().filter(p -> p.getId().equals(plantId)).findFirst()
                .orElse(plants.isEmpty() ? null : plants.get(0));

        Duration window = switch (range) {
            case "7d" -> Duration.ofDays(7);
            case "30d" -> Duration.ofDays(30);
            default -> { range = "24h"; yield Duration.ofHours(24); }
        };

        // chart data as plain maps (epoch millis) so the page doesn't depend on entity serialization
        List<Map<String, Object>> chartData = new ArrayList<>();
        List<Sensor> readings = new ArrayList<>();
        Map<Long, String> statuses = new LinkedHashMap<>();
        if (selected != null) {
            readings = sensorService.getSensorDataSince(selected.getId(), window);
            for (Sensor s : readings) {
                Map<String, Object> m = new LinkedHashMap<>();
                m.put("t", s.getRecordedAt().getTime());
                m.put("moisture", s.getSoilMoisture());
                m.put("temperature", s.getTemperature());
                m.put("humidity", s.getHumidity());
                chartData.add(m);
            }
        }

        // newest first, capped so the table stays readable
        List<Sensor> latest = new ArrayList<>(readings);
        java.util.Collections.reverse(latest);
        if (latest.size() > MAX_TABLE_ROWS) latest = new ArrayList<>(latest.subList(0, MAX_TABLE_ROWS));
        for (Sensor s : latest) {
            statuses.put(s.getId(), sensorService.evaluatePlantStatus(s, selected));
        }

        model.addAttribute("plants", plants);
        model.addAttribute("selectedPlant", selected);
        model.addAttribute("chartData", chartData);
        model.addAttribute("sensors", latest);
        model.addAttribute("statuses", statuses);
        model.addAttribute("totalReadings", readings.size());
        model.addAttribute("range", range);
        model.addAttribute("activePage", "sensor");
        return "main/sensor";
    }
}
