package com.csc3700.smartplant.service;

import com.csc3700.smartplant.model.WateringSchedule;
import com.csc3700.smartplant.repository.WateringScheduleRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;

/** Runs watering schedules whose time has passed and that are not completed yet. */
@Component
@ConditionalOnProperty(name = "smartplant.scheduler.enabled", havingValue = "true")
public class ScheduleRunner {
    private final WateringScheduleRepository repo;
    private final WateringService wateringService;

    public ScheduleRunner(WateringScheduleRepository repo, WateringService wateringService) {
        this.repo = repo;
        this.wateringService = wateringService;
    }

    @Scheduled(initialDelayString = "${smartplant.scheduler.interval-ms}",
               fixedRateString = "${smartplant.scheduler.interval-ms}")
    public void runDue() {
        for (WateringSchedule s : repo.findByCompletedFalseAndScheduleTimeBefore(Timestamp.from(Instant.now()))) {
            if (s.getPlant() != null && wateringService.water(s.getPlant(), null, "schedule #" + s.getId())) {
                s.setCompleted(true);
                repo.save(s);
            }
        }
    }
}
