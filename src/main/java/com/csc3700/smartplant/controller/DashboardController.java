package com.csc3700.smartplant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    // Show dashboard page
    @GetMapping("/dashboard")
    public String showDashboard() {
        return "dashboard"; // Will look for templates/dashboard.html
    }

    // Redirect root URL to dashboard
    @GetMapping("/")
    public String homeRedirect() {
        return "redirect:/dashboard";
    }
}

