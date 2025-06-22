package com.csc3700.smartplant.service;

import com.csc3700.smartplant.dto.SensorDto;
import com.csc3700.smartplant.model.Sensor;
import java.util.List;

public interface SensorService {
    List<Sensor> getAll();
    Sensor getById(Long id);
    Sensor save(Sensor sensor);
    void delete(Long id);

    void ingest(SensorDto dto);
}
