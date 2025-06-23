package com.csc3700.smartplant.controller;

import com.csc3700.smartplant.repository.PlantRepository;
import com.csc3700.smartplant.repository.SensorRepository;
import com.csc3700.smartplant.repository.WateringScheduleRepository;
import com.csc3700.smartplant.service.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Controller
public class DashboardController {

    private PlantRepository plantRepository;
    private WateringScheduleRepository wateringScheduleRepository;
    private SensorRepository sensorRepository;

    public DashboardController(PlantRepository plantRepository, WateringScheduleRepository wateringScheduleRepository, SensorRepository sensorRepository) {
        this.plantRepository = plantRepository;
        this.wateringScheduleRepository = wateringScheduleRepository;
        this.sensorRepository = sensorRepository;
    }

    // Show dashboard page
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
    }
}

