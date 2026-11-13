package com.csc3700.smartplant.controller;

import com.csc3700.smartplant.dto.SensorDto;
import com.csc3700.smartplant.service.SensorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/sensors")
public class SensorApiController {
  private final SensorService sensorService;
  public SensorApiController(SensorService sensorService) {
    this.sensorService = sensorService;
  }

  @PostMapping
  public ResponseEntity<String> ingest(@jakarta.validation.Valid @RequestBody SensorDto dto) {
    sensorService.ingest(dto);
    return ResponseEntity.ok("Data saved");
  }
}