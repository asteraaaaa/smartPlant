package com.csc3700.smartplant.controller;

import com.csc3700.smartplant.model.Plant;
import com.csc3700.smartplant.model.Sensor;
import com.csc3700.smartplant.service.PlantService;
import com.csc3700.smartplant.service.SensorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DashboardController {

    private final SensorService sensorService;
    private final PlantService plantService;

    public DashboardController(SensorService sensorService, PlantService plantService) {
        this.sensorService = sensorService;
        this.plantService = plantService;
    }

    @GetMapping("/")
    public String index(@RequestParam(required = false) Long plantId,
                        @RequestParam(defaultValue = "24h") String range,
                        Model model) {
        List<Plant> plants = plantService.getAll();

        for (Plant plant : plants) {
            Sensor latest = sensorService.getLatestByPlantId(plant.getId());
            plant.setStatus(latest == null ? "No data" : sensorService.evaluatePlantStatus(latest, plant));
        }

        Plant selected = plants.stream().filter(p -> p.getId().equals(plantId)).findFirst()
                .orElse(plants.isEmpty() ? null : plants.get(0));

        Duration window = switch (range) {
            case "7d" -> Duration.ofDays(7);
            case "30d" -> Duration.ofDays(30);
            default -> { range = "24h"; yield Duration.ofHours(24); }
        };

        // chart data as plain maps (epoch millis) so the page doesn't depend on entity serialization
        List<Map<String, Object>> chartData = new ArrayList<>();
        List<Sensor> recent = new ArrayList<>();
        if (selected != null) {
            List<Sensor> readings = sensorService.getSensorDataSince(selected.getId(), window);
            for (Sensor s : readings) {
                Map<String, Object> m = new LinkedHashMap<>();
                m.put("t", s.getRecordedAt().getTime());
                m.put("moisture", s.getSoilMoisture());
                m.put("temperature", s.getTemperature());
                m.put("humidity", s.getHumidity());
                chartData.add(m);
            }
            recent = sensorService.getRecentSensorDataByPlantId(selected.getId());
            recent.sort(Comparator.comparing(Sensor::getRecordedAt).reversed());
            if (recent.size() > 10) recent = new ArrayList<>(recent.subList(0, 10));
        }

        Map<Long, String> recentStatus = new LinkedHashMap<>();
        for (Sensor s : recent) {
            recentStatus.put(s.getId(), sensorService.evaluatePlantStatus(s, selected));
        }

        model.addAttribute("plants", plants);
        model.addAttribute("selectedPlant", selected);
        model.addAttribute("chartData", chartData);
        model.addAttribute("recentSensors", recent);
        model.addAttribute("recentStatus", recentStatus);
        model.addAttribute("range", range);
        model.addAttribute("activePage", "dashboard");
        return "main/dashboard";
    }
}
