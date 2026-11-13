package com.csc3700.smartplant.controller;

import com.csc3700.smartplant.model.Plant;
import com.csc3700.smartplant.model.WateringSchedule;
import com.csc3700.smartplant.service.PlantService;
import com.csc3700.smartplant.service.WateringScheduleService;
import com.csc3700.smartplant.service.WateringService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/schedule")
public class WateringScheduleController {

    private final WateringScheduleService wateringScheduleService;
    private final PlantService plantService;
    private final WateringService wateringService;

    public WateringScheduleController(WateringScheduleService wateringScheduleService, PlantService plantService,
                                      WateringService wateringService) {
        this.wateringScheduleService = wateringScheduleService;
        this.plantService = plantService;
        this.wateringService = wateringService;
    }

    @GetMapping("")
    public String listAllWateringSchedule(Model model) {
        List<Plant> plants = plantService.getAll();
        List<WateringSchedule> schedules = wateringScheduleService.getAll();
        LocalDate today = LocalDate.now();
        YearMonth currentMonth = YearMonth.of(today.getYear(), today.getMonth());

        // calendar header starts on Sunday: Sunday -> 0 blanks, Monday -> 1, ...
        int blanks = currentMonth.atDay(1).getDayOfWeek().getValue() % 7;

        List<WateringSchedule> todaySchedules = schedules.stream()
                .filter(s -> s.getScheduleDate() != null && s.getScheduleDate().equals(today))
                .sorted(Comparator.comparing(WateringSchedule::getScheduleTime,
                        Comparator.nullsLast(Comparator.naturalOrder())))
                .toList();

        model.addAttribute("currentMonth", currentMonth);
        model.addAttribute("daysInMonth", currentMonth.lengthOfMonth());
        model.addAttribute("blankDays", blanks);
        model.addAttribute("plants", plants);
        model.addAttribute("schedules", schedules);
        model.addAttribute("todaySchedules", todaySchedules);
        model.addAttribute("activePage", "schedule");
        return "main/schedule";
    }

    @PostMapping("/add")
    public String add(@RequestParam Long plantId,
                      @RequestParam(value = "when", required = false) String whenText,
                      @RequestParam(value = "minutes", defaultValue = "5") String minutesText,
                      RedirectAttributes ra) {
        int minutes;
        try {
            minutes = Integer.parseInt(minutesText.trim());
        } catch (NumberFormatException e) {
            ra.addFlashAttribute("error", "Invalid plant or duration (1-60 minutes)");
            return "redirect:/schedule";
        }
        LocalDateTime when;
        try {
            when = LocalDateTime.parse(whenText == null ? "" : whenText.trim());
        } catch (DateTimeParseException e) {
            ra.addFlashAttribute("error", "Invalid date/time - please pick a date and time");
            return "redirect:/schedule";
        }
        Plant plant = plantService.getById(plantId);
        if (plant == null || minutes < 1 || minutes > 60) {
            ra.addFlashAttribute("error", "Invalid plant or duration (1-60 minutes)");
            return "redirect:/schedule";
        }
        WateringSchedule s = new WateringSchedule();
        s.setPlant(plant);
        s.setScheduleDate(when.toLocalDate());
        s.setScheduleTime(Timestamp.valueOf(when));
        s.setDuration(Duration.ofMinutes(minutes));
        wateringScheduleService.save(s);
        ra.addFlashAttribute("message", "Scheduled " + plant.getName());
        return "redirect:/schedule";
    }

    @PostMapping("/complete/{id}")
    public String complete(@PathVariable Long id, RedirectAttributes ra) {
        WateringSchedule s = wateringScheduleService.getById(id);
        if (s == null) {
            ra.addFlashAttribute("error", "Schedule not found");
        } else if (s.isCompleted()) {
            ra.addFlashAttribute("error", "Already completed");
        } else {
            // schedule durations are minutes; the pump run is capped to the default so demos stay quick
            if (wateringService.water(s.getPlant(), null, "schedule #" + id)) {
                s.setCompleted(true);
                wateringScheduleService.save(s);
                ra.addFlashAttribute("message", "Watered " + s.getPlant().getName());
            } else {
                ra.addFlashAttribute("error", "Pump unreachable");
            }
        }
        return "redirect:/schedule";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        if (wateringScheduleService.getById(id) == null) {
            ra.addFlashAttribute("error", "Schedule not found");
        } else {
            wateringScheduleService.delete(id);
            ra.addFlashAttribute("message", "Schedule deleted");
        }
        return "redirect:/schedule";
    }
}
