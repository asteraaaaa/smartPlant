package com.csc3700.smartplant.service;

import com.csc3700.smartplant.model.SystemLog;
import com.csc3700.smartplant.repository.SystemLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemLogServiceImpl implements SystemLogService {
    @Autowired
    private SystemLogRepository repo;

    public List<SystemLog> getAll() {
        return repo.findAll();
    }

    public List<SystemLog> getRecent() {
        return repo.findTop200ByOrderByCreatedAtDesc();
    }

    public SystemLog getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public SystemLog save(SystemLog sl) {
        return repo.save(sl);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public void log(String eventType, String description) {
        SystemLog l = new SystemLog();
        l.setEventType(eventType);
        l.setDescription(description);
        repo.save(l);
    }
}
