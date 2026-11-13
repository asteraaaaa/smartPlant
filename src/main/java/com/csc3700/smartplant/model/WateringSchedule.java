package com.csc3700.smartplant.model;

import com.csc3700.smartplant.service.DurationAttributeConverter;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;

@Entity
@Table(name = "watering_schedule")
public class WateringSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "plant_id")
    private Plant plant;

    private LocalDate scheduleDate;
    private Timestamp scheduleTime;

    @Convert(converter = DurationAttributeConverter.class)
    private Duration duration;

    private boolean completed = false;

    public WateringSchedule() {
    }

    public WateringSchedule(Long id, Plant plant, LocalDate scheduleDate, Timestamp scheduleTime, Duration duration) {
        this.id = id;
        this.plant = plant;
        this.scheduleDate = scheduleDate;
        this.scheduleTime = scheduleTime;
        this.duration = duration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public LocalDate getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(LocalDate scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public Timestamp getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(Timestamp scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "WateringSchedule{" +
                "id=" + id +
                ", plant=" + plant +
                ", scheduleDate=" + scheduleDate +
                ", scheduleTime=" + scheduleTime +
                ", duration=" + duration +
                '}';
    }
}
