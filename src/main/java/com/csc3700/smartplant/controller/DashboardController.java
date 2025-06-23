package com.csc3700.smartplant.controller;

import com.csc3700.smartplant.dto.SensorDto;
import com.csc3700.smartplant.model.Plant;
import com.csc3700.smartplant.model.Sensor;
import com.csc3700.smartplant.repository.PlantRepository;
import com.csc3700.smartplant.repository.SensorRepository;
import com.csc3700.smartplant.repository.WateringScheduleRepository;
import com.csc3700.smartplant.service.PlantService;
import com.csc3700.smartplant.service.PlantServiceImpl;
import com.csc3700.smartplant.service.SensorService;
import com.csc3700.smartplant.service.SensorServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DashboardController {

    private final SensorService sensorService;
    private final SensorServiceImpl sensorServiceImpl;
    private PlantRepository plantRepository;
    private WateringScheduleRepository wateringScheduleRepository;
    private SensorRepository sensorRepository;
    private PlantService plantService;

    public DashboardController(PlantRepository plantRepository, WateringScheduleRepository wateringScheduleRepository, SensorRepository sensorRepository, SensorService sensorService, SensorServiceImpl sensorServiceImpl, PlantService plantService) {
        this.plantRepository = plantRepository;
        this.wateringScheduleRepository = wateringScheduleRepository;
        this.sensorRepository = sensorRepository;
        this.sensorService = sensorService;
        this.sensorServiceImpl = sensorServiceImpl;
        this.plantService = plantService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Plant> plants = plantRepository.findAll();
        List<Sensor> sensors = sensorRepository.findAll();
        List<Sensor> statuses = new ArrayList<>();

        if (!plants.isEmpty()) {
            Plant selectedPlant = plants.get(0);
            List<Sensor> sensorDataList = sensorService.getRecentSensorDataByPlantId(selectedPlant.getId());

            model.addAttribute("selectedPlant", selectedPlant);
            model.addAttribute("sensorDataList", sensorDataList);
        }

        for (Plant plant: plants){
            Sensor sensor = sensorService.getById(plant.getId());
            if (sensor != null) {
                String status = sensorServiceImpl.evaluatePlantStatus(sensor, plant);
                plant.setStatus(status);
            }
        }

        model.addAttribute("statuses", statuses);
        model.addAttribute("plants", plants);
        model.addAttribute("sensors", sensors);
        model.addAttribute("activePage", "dashboard");
        return "main/dashboard";
    }

    /* Show dashboard page
    @GetMapping("/")
    public String showDashboard(Model model) {
        long totalPlants = plantRepository.count();
        long scheduleCount = wateringScheduleRepository.count();
        long activeSensors = sensorRepository.countByRecordedAtAfter(
                Timestamp.from(Instant.now().minus(1, ChronoUnit.HOURS)));
        model.addAttribute("activeSensors", activeSensors);
        model.addAttribute("scheduleCount", scheduleCount);
        model.addAttribute("totalPlants", totalPlants);
        model.addAttribute("activePage", "dashboard");
        return "main/dashboard";
    }*/
}

