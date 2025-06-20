package com.csc3700.smartplant.repository;

import com.csc3700.smartplant.model.WateringSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WateringScheduleRepository extends JpaRepository<WateringSchedule, Long> {}