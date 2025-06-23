package com.csc3700.smartplant.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "plants")
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private float minOptimalMoisture;  // Added min moisture
    private float maxOptimalMoisture;  // Added max moisture
    private float minOptimalTemperature; // Added min temperature
    private float maxOptimalTemperature; // Added max temperature
    private float minOptimalHumidity;  // Added min humidity
    private float maxOptimalHumidity;  // Added max humidity
    private Integer wateringFrequency;
    private LocalDate lastWatered;

    @Transient
    private String status;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", columnDefinition = "VARCHAR(255)")
    private PlantType type;

    public Plant() {
    }

    // Updated constructor with range parameters
    public Plant(Long id, String name, PlantType type,
                 float minOptimalMoisture, float maxOptimalMoisture,
                 float minOptimalTemperature, float maxOptimalTemperature,
                 float minOptimalHumidity, float maxOptimalHumidity,
                 Integer wateringFrequency, LocalDate lastWatered, String status) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.minOptimalMoisture = minOptimalMoisture;
        this.maxOptimalMoisture = maxOptimalMoisture;
        this.minOptimalTemperature = minOptimalTemperature;
        this.maxOptimalTemperature = maxOptimalTemperature;
        this.minOptimalHumidity = minOptimalHumidity;
        this.maxOptimalHumidity = maxOptimalHumidity;
        this.wateringFrequency = wateringFrequency;
        this.lastWatered = lastWatered;
        this.status = status;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public PlantType getType() { return type; }
    public void setType(PlantType type) { this.type = type; }

    // Moisture range accessors
    public float getMinOptimalMoisture() { return minOptimalMoisture; }
    public void setMinOptimalMoisture(float minOptimalMoisture) {
        this.minOptimalMoisture = minOptimalMoisture;
    }
    public float getMaxOptimalMoisture() { return maxOptimalMoisture; }
    public void setMaxOptimalMoisture(float maxOptimalMoisture) {
        this.maxOptimalMoisture = maxOptimalMoisture;
    }

    // Temperature range accessors
    public float getMinOptimalTemperature() { return minOptimalTemperature; }
    public void setMinOptimalTemperature(float minOptimalTemperature) {
        this.minOptimalTemperature = minOptimalTemperature;
    }
    public float getMaxOptimalTemperature() { return maxOptimalTemperature; }
    public void setMaxOptimalTemperature(float maxOptimalTemperature) {
        this.maxOptimalTemperature = maxOptimalTemperature;
    }

    // Humidity range accessors
    public float getMinOptimalHumidity() { return minOptimalHumidity; }
    public void setMinOptimalHumidity(float minOptimalHumidity) {
        this.minOptimalHumidity = minOptimalHumidity;
    }
    public float getMaxOptimalHumidity() { return maxOptimalHumidity; }
    public void setMaxOptimalHumidity(float maxOptimalHumidity) {
        this.maxOptimalHumidity = maxOptimalHumidity;
    }

    public Integer getWateringFrequency() { return wateringFrequency; }
    public void setWateringFrequency(Integer wateringFrequency) {
        this.wateringFrequency = wateringFrequency;
    }

    public LocalDate getLastWatered() {
        return lastWatered;
    }

    public void setLastWatered(LocalDate lastWatered) {
        this.lastWatered = lastWatered;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Plant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", minOptimalMoisture=" + minOptimalMoisture +
                ", maxOptimalMoisture=" + maxOptimalMoisture +
                ", minOptimalTemperature=" + minOptimalTemperature +
                ", maxOptimalTemperature=" + maxOptimalTemperature +
                ", minOptimalHumidity=" + minOptimalHumidity +
                ", maxOptimalHumidity=" + maxOptimalHumidity +
                ", wateringFrequency=" + wateringFrequency +
                ", lastWatered=" + lastWatered +
                ", status='" + status + '\'' +
                '}';
    }
}