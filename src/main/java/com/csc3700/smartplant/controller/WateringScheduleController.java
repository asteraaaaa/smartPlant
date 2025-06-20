package com.csc3700.smartplant.controller;

import com.csc3700.smartplant.model.Plant;
import com.csc3700.smartplant.model.WateringSchedule;
import com.csc3700.smartplant.service.PlantService;
import com.csc3700.smartplant.service.WateringScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/watering")
public class WateringScheduleController {
    @Autowired
    private WateringScheduleService service;

    @GetMapping
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
    }
}