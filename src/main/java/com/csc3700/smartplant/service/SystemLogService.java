package com.csc3700.smartplant.service;

import com.csc3700.smartplant.model.SystemLog;
import java.util.List;

public interface SystemLogService {
    List<SystemLog> getAll();
    SystemLog getById(Long id);
    SystemLog save(SystemLog log);
    void delete(Long id);
}
