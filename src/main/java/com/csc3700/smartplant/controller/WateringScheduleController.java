package com.csc3700.smartplant.controller;

import com.csc3700.smartplant.model.Plant;
import com.csc3700.smartplant.model.WateringSchedule;
import com.csc3700.smartplant.service.PlantService;
import com.csc3700.smartplant.service.WateringScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/schedule")
public class WateringScheduleController {

    @Autowired
    private final WateringScheduleService wateringScheduleService;
    private final PlantService plantService;

    public WateringScheduleController(WateringScheduleService wateringScheduleService, PlantService plantService) {
        this.wateringScheduleService = wateringScheduleService;
        this.plantService = plantService;
    }

    @GetMapping("")
    public String listAllWateringSchedule(Model model) {
        List<Plant> plants = plantService.getAll();
        List<WateringSchedule> schedules = wateringScheduleService.getAll();

        model.addAttribute("plants", plants);
        model.addAttribute("schedules", schedules);
        model.addAttribute("activePage", "schedule");
        return "main/wateringSchedule";
    }

    /*@GetMapping
    public List<WateringSchedule> all() {
        return service.getAll();
    }

    @GetMapping("/{id}") public WateringSchedule one(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public WateringSchedule save(@RequestBody WateringSchedule ws) {
        return service.save(ws);
    }

    @DeleteMapping("/{id}") public void delete(@PathVariable Long id) {
        service.delete(id);
    }*/
}