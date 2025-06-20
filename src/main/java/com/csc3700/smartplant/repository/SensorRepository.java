package com.csc3700.smartplant.repository;

import com.csc3700.smartplant.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, Long> {}