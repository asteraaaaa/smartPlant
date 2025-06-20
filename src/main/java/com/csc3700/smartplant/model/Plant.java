package com.csc3700.smartplant.model;

import jakarta.persistence.*;

@Entity
@Table(name = "plants")
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    private float optimalMoistureLevel;
    private float optimalTemperature;
    private float optimalHumidity;

    public Plant() {
    }

    public Plant(Long id, String name, String type, float optimalMoistureLevel, float optimalTemperature, float optimalHumidity) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.optimalMoistureLevel = optimalMoistureLevel;
        this.optimalTemperature = optimalTemperature;
        this.optimalHumidity = optimalHumidity;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    @Override
    public String toString() {
        return "Plant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", optimalMoistureLevel=" + optimalMoistureLevel +
                ", optimalTemperature=" + optimalTemperature +
                ", optimalHumidity=" + optimalHumidity +
                '}';
    }
}
