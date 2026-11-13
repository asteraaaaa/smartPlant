package com.csc3700.smartplant.repository;

import com.csc3700.smartplant.model.SystemLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SystemLogRepository extends JpaRepository<SystemLog, Long> {
    List<SystemLog> findTop200ByOrderByCreatedAtDesc();
}
