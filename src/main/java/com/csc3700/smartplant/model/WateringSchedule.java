package com.csc3700.smartplant.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.Duration;

@Entity
@Table(name = "watering_schedule")
public class WateringSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "plant_id")
    private Plant plant;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Timestamp scheduleTime;
    private Duration duration;

    public WateringSchedule() {
    }

    public WateringSchedule(Long id, Plant plant, User user, Timestamp scheduleTime, Duration duration) {
        this.id = id;
        this.plant = plant;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(Timestamp scheduleTime) {
        this.scheduleTime = scheduleTime;
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
                ", user=" + user +
                ", scheduleTime=" + scheduleTime +
                ", duration=" + duration +
                '}';
    }
}
