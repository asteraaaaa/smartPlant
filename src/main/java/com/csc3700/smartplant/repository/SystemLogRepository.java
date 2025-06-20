package com.csc3700.smartplant.repository;

import com.csc3700.smartplant.model.SystemLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemLogRepository extends JpaRepository<SystemLog, Long> {}