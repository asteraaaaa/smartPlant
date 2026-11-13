package com.csc3700.smartplant.dto;

import com.csc3700.smartplant.model.Plant;
import com.csc3700.smartplant.model.Sensor;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class SensorDto {
    @NotNull(message = "plantId is required")
    private Long   plantId;
    @DecimalMin("0") @DecimalMax("100")
    private float  soilMoisture;
    @DecimalMin("-40") @DecimalMax("80")
    private float  temperature;
    @DecimalMin("0") @DecimalMax("100")
    private float  humidity;

    // Getters & setters
    public Long getPlantId() { return plantId; }
    public void setPlantId(Long plantId) { this.plantId = plantId; }
    public float getSoilMoisture() { return soilMoisture; }
    public void setSoilMoisture(float soilMoisture) { this.soilMoisture = soilMoisture; }
    public float getTemperature() { return temperature; }
    public void setTemperature(float temperature) { this.temperature = temperature; }
    public float getHumidity() { return humidity; }
    public void setHumidity(float humidity) { this.humidity = humidity; }

    // Convert DTO → JPA entity
    public Sensor toEntity(Plant plant) {
        Sensor s = new Sensor();
        s.setPlant(plant);
        s.setSoilMoisture(soilMoisture);
        s.setTemperature(temperature);
        s.setHumidity(humidity);
        return s;
    }
}