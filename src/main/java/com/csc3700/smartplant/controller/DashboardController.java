package com.csc3700.smartplant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    // Show dashboard page
    @GetMapping("/")
    public String showDashboard() {
        return "main/template"; // Will look for templates/dashboard.html
    }
}

