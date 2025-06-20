package com.csc3700.smartplant.repository;

import com.csc3700.smartplant.model.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantRepository extends JpaRepository<Plant, Long> {}