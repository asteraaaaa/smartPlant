package com.csc3700.smartplant.controller;

import com.csc3700.smartplant.model.Plant;
import com.csc3700.smartplant.model.PlantType;
import com.csc3700.smartplant.model.Sensor;
import com.csc3700.smartplant.service.PlantService;
import com.csc3700.smartplant.service.SensorService;
import com.csc3700.smartplant.service.SystemLogService;
import com.csc3700.smartplant.service.WateringService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/plant")
public class PlantController {
    private final PlantService plantService;
    private final SensorService sensorService;
    private final SystemLogService logService;
    private final WateringService wateringService;

    public PlantController(PlantService plantService, SensorService sensorService,
                           SystemLogService logService, WateringService wateringService) {
        this.plantService = plantService;
        this.sensorService = sensorService;
        this.logService = logService;
        this.wateringService = wateringService;
    }

    @GetMapping("")
    public String listAllPlant(Model model) {
        List<Plant> plants = plantService.getAll();
        for (Plant p : plants) {
            Sensor s = sensorService.getLatestByPlantId(p.getId());
            p.setStatus(s == null ? "No data" : sensorService.evaluatePlantStatus(s, p));
        }
        model.addAttribute("plants", plants);
        model.addAttribute("activePage", "plant");
        return "main/plant";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        Plant p = new Plant();
        p.setMinOptimalMoisture(40);
        p.setMaxOptimalMoisture(70);
        p.setMinOptimalTemperature(18);
        p.setMaxOptimalTemperature(28);
        p.setMinOptimalHumidity(40);
        p.setMaxOptimalHumidity(70);
        p.setWateringFrequency(3);
        return form(model, p, "Add New Plant");
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes ra) {
        Plant p = plantService.getById(id);
        if (p == null) {
            ra.addFlashAttribute("error", "Plant not found");
            return "redirect:/plant";
        }
        return form(model, p, "Edit Plant");
    }

    private String form(Model model, Plant plant, String title) {
        model.addAttribute("plant", plant);
        model.addAttribute("title", title);
        model.addAttribute("types", PlantType.values());
        model.addAttribute("activePage", "plant");
        return "main/plantForm";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Plant plant, RedirectAttributes ra, Model model) {
        String error = validate(plant);
        if (error != null) {
            model.addAttribute("error", error);
            return form(model, plant, plant.getId() == null ? "Add New Plant" : "Edit Plant");
        }
        boolean isNew = plant.getId() == null;
        if (!isNew) {
            // keep fields the form doesn't edit
            Plant existing = plantService.getById(plant.getId());
            if (existing != null) plant.setLastWatered(existing.getLastWatered());
        }
        plantService.save(plant);
        logService.log("PLANT", (isNew ? "Added plant " : "Updated plant ") + plant.getName());
        ra.addFlashAttribute("message", "Plant saved: " + plant.getName());
        return "redirect:/plant";
    }

    private String validate(Plant p) {
        if (p.getName() == null || p.getName().isBlank()) return "Name is required";
        if (p.getType() == null) return "Type is required";
        if (p.getMinOptimalMoisture() > p.getMaxOptimalMoisture()) return "Min moisture must not exceed max";
        if (p.getMinOptimalTemperature() > p.getMaxOptimalTemperature()) return "Min temperature must not exceed max";
        if (p.getMinOptimalHumidity() > p.getMaxOptimalHumidity()) return "Min humidity must not exceed max";
        if (p.getWateringFrequency() != null && p.getWateringFrequency() < 1) return "Watering frequency must be at least 1 day";
        return null;
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        Plant p = plantService.getById(id);
        if (p != null) {
            plantService.delete(id);
            logService.log("PLANT", "Deleted plant " + p.getName());
            ra.addFlashAttribute("message", "Deleted " + p.getName());
        }
        return "redirect:/plant";
    }

    @PostMapping("/water/{id}")
    public String water(@PathVariable Long id, RedirectAttributes ra) {
        Plant p = plantService.getById(id);
        if (p == null) {
            ra.addFlashAttribute("error", "Plant not found");
        } else if (wateringService.water(p, null, "manual")) {
            ra.addFlashAttribute("message", "Watered " + p.getName());
        } else {
            ra.addFlashAttribute("error", "Pump unreachable");
        }
        return "redirect:/plant";
    }
}
