package com.csc3700.smartplant.service;

import com.csc3700.smartplant.model.Sensor;
import com.csc3700.smartplant.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensorServiceImpl implements SensorService {
    @Autowired
    private SensorRepository repo;

    public List<Sensor> getAll() {
        return repo.findAll();
    }

    public Sensor getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Sensor save(Sensor s) {
        return repo.save(s);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
