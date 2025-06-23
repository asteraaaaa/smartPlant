package com.csc3700.smartplant.controller;

import com.csc3700.smartplant.model.Plant;
import com.csc3700.smartplant.model.User;
import com.csc3700.smartplant.service.PlantService;
import com.csc3700.smartplant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/plant")
public class PlantController {
    @Autowired
    private PlantService plantService;

    // Show dashboard page
    @GetMapping("")
    public String listAllPlant(Model model) {
        List<Plant> plants = plantService.getAll(); // Or however you retrieve data
        model.addAttribute("plants", plants);
        model.addAttribute("activePage", "plant");
        return "main/plant"; // Will look for templates/dashboard.html
    }

//    @GetMapping
//    public List<Plant> all() {
//        return service.getAll();
//    }
//
//    @GetMapping("/{id}") public Plant one(@PathVariable Long id) {
//        return service.getById(id);
//    }
//
//    @PostMapping
//    public Plant save(@RequestBody Plant plant) {
//        return service.save(plant);
//    }
//
//    @DeleteMapping("/{id}") public void delete(@PathVariable Long id) {
//        service.delete(id);
//    }
}
