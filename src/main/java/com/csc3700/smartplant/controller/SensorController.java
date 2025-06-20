package com.csc3700.smartplant.controller;

import com.csc3700.smartplant.model.Plant;
import com.csc3700.smartplant.model.Sensor;
import com.csc3700.smartplant.service.PlantService;
import com.csc3700.smartplant.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sensors")
public class SensorController {
    @Autowired
    private SensorService service;

    @GetMapping
    public List<Sensor> all() {
        return service.getAll();
    }

    @GetMapping("/{id}") public Sensor one(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Sensor save(@RequestBody Sensor sensor) {
        return service.save(sensor);
    }

    @DeleteMapping("/{id}") public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

