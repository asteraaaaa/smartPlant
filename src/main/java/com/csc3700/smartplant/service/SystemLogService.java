package com.csc3700.smartplant.service;

import com.csc3700.smartplant.model.SystemLog;
import java.util.List;

public interface SystemLogService {
    List<SystemLog> getAll();
    List<SystemLog> getRecent();
    SystemLog getById(Long id);
    SystemLog save(SystemLog sl);
    void delete(Long id);
    /** Convenience: record an event (eventType e.g. PUMP, WARNING, SENSOR, PLANT). */
    void log(String eventType, String description);
}
