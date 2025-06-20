package com.csc3700.smartplant.service;

import com.csc3700.smartplant.model.Plant;
import java.util.List;

public interface PlantService {
    List<Plant> getAll();
    Plant getById(Long id);
    Plant save(Plant plant);
    void delete(Long id);
}
