package com.csc3700.smartplant.repository;

import com.csc3700.smartplant.model.WateringSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface WateringScheduleRepository extends JpaRepository<WateringSchedule, Long> {
    void deleteByPlantId(Long plantId);

    List<WateringSchedule> findByCompletedFalseAndScheduleTimeBefore(Timestamp time);
}
