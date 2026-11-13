package com.csc3700.smartplant.service;

import com.csc3700.smartplant.model.Plant;
import com.csc3700.smartplant.repository.PlantRepository;
import com.csc3700.smartplant.repository.SensorRepository;
import com.csc3700.smartplant.repository.WateringScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlantServiceImpl implements PlantService {
    @Autowired
    private PlantRepository repo;
    @Autowired
    private SensorRepository sensorRepo;
    @Autowired
    private WateringScheduleRepository scheduleRepo;

    public List<Plant> getAll() {
        return repo.findAll();
    }

    public Plant getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Plant save(Plant p) {
        return repo.save(p);
    }

    @Transactional
    public void delete(Long id) {
        // remove dependent rows first (foreign keys)
        sensorRepo.deleteByPlantId(id);
        scheduleRepo.deleteByPlantId(id);
        repo.deleteById(id);
    }
}
