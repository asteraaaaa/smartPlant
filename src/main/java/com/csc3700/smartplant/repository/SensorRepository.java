package com.csc3700.smartplant.repository;

import com.csc3700.smartplant.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
    long countByRecordedAtAfter(Timestamp from);

    List<Sensor> findByPlantIdOrderByRecordedAtAsc(Long plantId);
}