package com.csc3700.smartplant.controller;

import com.csc3700.smartplant.model.Plant;
import com.csc3700.smartplant.model.SystemLog;
import com.csc3700.smartplant.service.PlantService;
import com.csc3700.smartplant.service.SystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/systems")
public class SystemLogController {
    @Autowired
    private SystemLogService service;

    @GetMapping("")
    public String listAllSystemLog(Model model) {
        model.addAttribute("activePage", "systems");
        return "main/systemLogs"; // Will look for templates/dashboard.html
    }

    /*@GetMapping
    public List<SystemLog> all() {
        return service.getAll();
    }

    @GetMapping("/{id}") public SystemLog one(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public SystemLog save(@RequestBody SystemLog systemLog) {
        return service.save(systemLog);
    }

    @DeleteMapping("/{id}") public void delete(@PathVariable Long id) {
        service.delete(id);
    }*/
}
