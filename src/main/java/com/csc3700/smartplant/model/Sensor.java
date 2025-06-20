package com.csc3700.smartplant.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "sensors")
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "plant_id")
    private Plant plant;

    private float soilMoisture;
    private float temperature;
    private float humidity;
    private Timestamp recordedAt = Timestamp.from(Instant.now());

    public Sensor() {
    }

    public Sensor(Long id, Plant plant, float soilMoisture, float temperature, float humidity, Timestamp recordedAt) {
        this.id = id;
        this.plant = plant;
        this.soilMoisture = soilMoisture;
        this.temperature = temperature;
        this.humidity = humidity;
        this.recordedAt = recordedAt;
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

    public float getSoilMoisture() {
        return soilMoisture;
    }

    public void setSoilMoisture(float soilMoisture) {
        this.soilMoisture = soilMoisture;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public Timestamp getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(Timestamp recordedAt) {
        this.recordedAt = recordedAt;
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "id=" + id +
                ", plant=" + plant +
                ", soilMoisture=" + soilMoisture +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", recordedAt=" + recordedAt +
                '}';
    }
}
