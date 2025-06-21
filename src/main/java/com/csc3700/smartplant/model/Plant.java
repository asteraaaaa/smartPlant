package com.csc3700.smartplant.model;

import jakarta.persistence.*;

@Entity
@Table(name = "plants")
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private float optimalMoistureLevel;
    private float optimalTemperature;
    private float optimalHumidity;
    private Integer wateringFrequency;

    @Enumerated(EnumType.STRING)  // Store as string in DB
    @Column(name = "type", columnDefinition = "VARCHAR(255)")
    private PlantType type;  // Renamed for clarity

    public Plant() {
    }

    public Plant(Long id, String name, PlantType type, float optimalMoistureLevel, float optimalTemperature, float optimalHumidity, Integer wateringFrequency) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.optimalMoistureLevel = optimalMoistureLevel;
        this.optimalTemperature = optimalTemperature;
        this.optimalHumidity = optimalHumidity;
        this.wateringFrequency = wateringFrequency;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlantType getType() {
        return type;
    }

    public void setType(PlantType type) {
        this.type = type;
    }

    public float getOptimalMoistureLevel() {
        return optimalMoistureLevel;
    }

    public void setOptimalMoistureLevel(float optimalMoistureLevel) {
        this.optimalMoistureLevel = optimalMoistureLevel;
    }

    public float getOptimalTemperature() {
        return optimalTemperature;
    }

    public void setOptimalTemperature(float optimalTemperature) {
        this.optimalTemperature = optimalTemperature;
    }

    public float getOptimalHumidity() {
        return optimalHumidity;
    }

    public void setOptimalHumidity(float optimalHumidity) {
        this.optimalHumidity = optimalHumidity;
    }

    public Integer getWateringFrequency() {
        return wateringFrequency;
    }

    public void setWateringFrequency(Integer wateringFrequency) {
        this.wateringFrequency = wateringFrequency;
    }

    @Override
    public String toString() {
        return "Plant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", optimalMoistureLevel=" + optimalMoistureLevel +
                ", optimalTemperature=" + optimalTemperature +
                ", optimalHumidity=" + optimalHumidity +
                ", wateringFrequency=" + wateringFrequency +
                '}';
    }
}
