package com.csc3700.smartplant.controller;

import com.csc3700.smartplant.service.SystemLogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/systems")
public class SystemLogController {
    private final SystemLogService service;

    public SystemLogController(SystemLogService service) {
        this.service = service;
    }

    @GetMapping("")
    public String listAllSystemLog(Model model) {
        model.addAttribute("logs", service.getRecent());
        model.addAttribute("activePage", "systems");
        return "main/systemLogs";
    }
}
