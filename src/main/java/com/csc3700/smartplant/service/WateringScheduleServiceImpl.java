package com.csc3700.smartplant.service;

import com.csc3700.smartplant.model.User;
import com.csc3700.smartplant.model.WateringSchedule;
import com.csc3700.smartplant.repository.UserRepository;
import com.csc3700.smartplant.repository.WateringScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WateringScheduleServiceImpl implements WateringScheduleService {
    @Autowired
    private WateringScheduleRepository repo;

    public List<WateringSchedule> getAll() {
        return repo.findAll();
    }

    public WateringSchedule getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public WateringSchedule save(WateringSchedule w) {
        return repo.save(w);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
