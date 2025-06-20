package com.csc3700.smartplant.service;

import com.csc3700.smartplant.model.Plant;
import com.csc3700.smartplant.model.User;
import com.csc3700.smartplant.repository.PlantRepository;
import com.csc3700.smartplant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlantServiceImpl implements PlantService {
    @Autowired
    private PlantRepository repo;

    public List<Plant> getAll() {
        return repo.findAll();
    }

    public Plant getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Plant save(Plant p) {
        return repo.save(p);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
