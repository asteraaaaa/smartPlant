package com.csc3700.smartplant.controller;

import com.csc3700.smartplant.dto.SensorDto;
import com.csc3700.smartplant.service.SensorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sensors")
public class SensorApiController {
  private final SensorService sensorService;
  public SensorApiController(SensorService sensorService) {
    this.sensorService = sensorService;
  }

  @PostMapping
  public ResponseEntity<Void> ingest(@RequestBody SensorDto dto) {
    sensorService.ingest(dto);
    return ResponseEntity.ok().build();
  }
}