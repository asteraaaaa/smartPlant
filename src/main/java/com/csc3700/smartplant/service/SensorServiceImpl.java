package com.csc3700.smartplant.service;

import com.csc3700.smartplant.dto.SensorDto;
import com.csc3700.smartplant.model.Plant;
import com.csc3700.smartplant.model.Sensor;
import com.csc3700.smartplant.repository.PlantRepository;
import com.csc3700.smartplant.repository.SensorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensorServiceImpl implements SensorService {
    private final SensorRepository sensorRepo;
    private final PlantRepository  plantRepo;
    private final PumpClientService pumpClient;

    @Autowired
    public SensorServiceImpl(SensorRepository sensorRepo,
                             PlantRepository plantRepo,
                             PumpClientService pumpClient) {
        this.sensorRepo  = sensorRepo;
        this.plantRepo   = plantRepo;
        this.pumpClient  = pumpClient;
    }

    @Override
    public List<Sensor> getAll() {
        return sensorRepo.findAll();
    }

    @Override
    public Sensor getById(Long id) {
        return sensorRepo.findById(id).orElse(null);
    }

    @Override
    public Sensor save(Sensor sensor) {
        return sensorRepo.save(sensor);
    }

    @Override
    public void delete(Long id) {
        sensorRepo.deleteById(id);
    }

    @Override
    public void ingest(SensorDto dto) {
        // 1. Lookup Plant
        Plant plant = plantRepo.findById(dto.getPlantId())
                .orElseThrow(() ->
                        new EntityNotFoundException("No such plant: " + dto.getPlantId()));

        // 2. Convert DTO → Entity & save
        Sensor sensor = dto.toEntity(plant);
        sensorRepo.save(sensor);

        // 3. Business logic: pump if too dry
        if (sensor.getSoilMoisture() < plant.getMinOptimalMoisture()) {
            pumpClient.turnOnPump(5000);
        }
    }

    @Override
    public String evaluatePlantStatus(Sensor sensor, Plant plant) {
        int issues = 0;

        if (sensor.getSoilMoisture() < plant.getMinOptimalMoisture() || sensor.getSoilMoisture() > plant.getMaxOptimalMoisture()) issues++;
        if (sensor.getTemperature() < plant.getMinOptimalTemperature() || sensor.getTemperature() > plant.getMaxOptimalTemperature()) issues++;
        if (sensor.getHumidity() < plant.getMinOptimalHumidity() || sensor.getHumidity() > plant.getMaxOptimalHumidity()) issues++;

        if (issues == 0) return "Healthy";
        else if (issues == 1) return "Warning";
        else return "Critical";
    }

    @Override
    public List<Sensor> getRecentSensorDataByPlantId(Long plantId) {
        // You can limit to last 24h / 7d etc., if needed
        return sensorRepo.findByPlantIdOrderByRecordedAtAsc(plantId);
    }
}
