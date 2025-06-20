package com.csc3700.smartplant.service;

import com.csc3700.smartplant.model.WateringSchedule;
import java.util.List;

public interface WateringScheduleService {
    List<WateringSchedule> getAll();
    WateringSchedule getById(Long id);
    WateringSchedule save(WateringSchedule ws);
    void delete(Long id);
}
