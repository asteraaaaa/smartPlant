package com.csc3700.smartplant;

import com.csc3700.smartplant.model.Plant;
import com.csc3700.smartplant.model.Sensor;
import com.csc3700.smartplant.repository.SensorRepository;
import com.csc3700.smartplant.service.PumpClientService;
import com.csc3700.smartplant.service.SensorServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/** Functional tests: does the system do what it is supposed to do? */
@SpringBootTest(properties = {"smartplant.simulator.enabled=false", "smartplant.scheduler.enabled=false"})
@AutoConfigureMockMvc
class SmartPlantFunctionalTests {

    @Autowired MockMvc mvc;
    @Autowired SensorRepository sensorRepo;
    @Autowired com.csc3700.smartplant.repository.PlantRepository plantRepo;
    @Autowired SensorServiceImpl sensorService;
    @MockitoBean PumpClientService pump;   // don't call the real Arduino

    // ---------- Pages ----------
    @Test void FT01_dashboardLoads() throws Exception {
        mvc.perform(get("/")).andExpect(status().isOk())
           .andExpect(view().name("main/dashboard"))
           .andExpect(model().attributeExists("plants", "recentSensors", "selectedPlant"));
    }

    @Test void FT02_plantPageListsSeededPlants() throws Exception {
        mvc.perform(get("/plant")).andExpect(status().isOk())
           .andExpect(view().name("main/plant"))
           .andExpect(content().string(containsString("Basil")))
           .andExpect(content().string(containsString("Spider Plant")));
    }

    @Test void FT03_sensorPageLoads() throws Exception {
        mvc.perform(get("/sensor")).andExpect(status().isOk())
           .andExpect(view().name("main/sensor"));
    }

    @Test void FT04_schedulePageLoads() throws Exception {
        mvc.perform(get("/schedule")).andExpect(status().isOk())
           .andExpect(view().name("main/schedule"))
           .andExpect(model().attributeExists("daysInMonth", "schedules", "plants"));
    }

    @Test void FT05_systemLogsPageLoads() throws Exception {
        mvc.perform(get("/systems")).andExpect(status().isOk())
           .andExpect(view().name("main/systemLogs"));
    }

    // ---------- Plant status rules ----------
    private Plant plant() {   // moisture 30-60, temp 18-30, humidity 40-70
        Plant p = new Plant();
        p.setMinOptimalMoisture(30); p.setMaxOptimalMoisture(60);
        p.setMinOptimalTemperature(18); p.setMaxOptimalTemperature(30);
        p.setMinOptimalHumidity(40); p.setMaxOptimalHumidity(70);
        return p;
    }
    private Sensor reading(float m, float t, float h) {
        Sensor s = new Sensor(); s.setSoilMoisture(m); s.setTemperature(t); s.setHumidity(h); return s;
    }

    @Test void FT06_statusHealthy() {
        assertEquals("Healthy", sensorService.evaluatePlantStatus(reading(45, 25, 50), plant()));
    }
    @Test void FT07_statusWarningOneIssue() {
        assertEquals("Warning", sensorService.evaluatePlantStatus(reading(10, 25, 50), plant()));
    }
    @Test void FT08_statusCriticalTwoOrMoreIssues() {
        assertEquals("Critical", sensorService.evaluatePlantStatus(reading(10, 40, 50), plant()));
        assertEquals("Critical", sensorService.evaluatePlantStatus(reading(10, 40, 99), plant()));
    }
    @Test void FT09_statusBoundariesAreInclusiveHealthy() {
        assertEquals("Healthy", sensorService.evaluatePlantStatus(reading(30, 18, 40), plant()));
        assertEquals("Healthy", sensorService.evaluatePlantStatus(reading(60, 30, 70), plant()));
    }

    // ---------- Sensor ingest API ----------
    @Test void FT10_ingestStoresReadingAndSkipsPumpWhenMoistureOk() throws Exception {
        long before = sensorRepo.count();
        mvc.perform(post("/api/sensors").contentType(MediaType.APPLICATION_JSON)
                .content("{\"plantId\":1,\"soilMoisture\":50,\"temperature\":25,\"humidity\":50}"))
           .andExpect(status().isOk()).andExpect(content().string("Data saved"));
        assertEquals(before + 1, sensorRepo.count());
        verify(pump, never()).turnOnPump(anyLong());
    }

    @Test void FT11_ingestTriggersPumpWhenSoilTooDry() throws Exception {
        mvc.perform(post("/api/sensors").contentType(MediaType.APPLICATION_JSON)
                .content("{\"plantId\":2,\"soilMoisture\":10,\"temperature\":25,\"humidity\":50}"))
           .andExpect(status().isOk());
        verify(pump).turnOnPump(5000);
    }

    @Test void FT12_ingestUnknownPlantIsRejectedGracefully() throws Exception {
        // Expected: a 4xx (e.g. 404). A 500 means the error isn't handled.
        mvc.perform(post("/api/sensors").contentType(MediaType.APPLICATION_JSON)
                .content("{\"plantId\":999,\"soilMoisture\":50,\"temperature\":25,\"humidity\":50}"))
           .andExpect(status().is4xxClientError());
    }

    @Test void FT13_ingestMalformedJsonReturns400() throws Exception {
        mvc.perform(post("/api/sensors").contentType(MediaType.APPLICATION_JSON).content("not json"))
           .andExpect(status().isBadRequest());
    }

    @Test void FT14_usersApiNoLongerExists() throws Exception {
        // the Users API (which stored plaintext passwords) was removed
        mvc.perform(get("/api/users")).andExpect(status().isNotFound());
    }

    @Test void FT16_dashboardStatusUsesLatestReadingOfEachPlant() throws Exception {
        // Seed: plant 2 (Basil) latest reading is moisture 30 < min 50 => 1 issue => Warning.
        // Add a newer, healthy reading for plant 2 and a plant-3-only check: status must follow plant's own latest.
        Sensor s = new Sensor();
        s.setPlant(plantRepo.findById(2L).orElseThrow());
        s.setSoilMoisture(60); s.setTemperature(25); s.setHumidity(50);
        s.setRecordedAt(java.sql.Timestamp.from(java.time.Instant.now()));
        sensorRepo.save(s);
        mvc.perform(get("/")).andExpect(status().isOk())
           .andExpect(model().attribute("plants", org.hamcrest.Matchers.hasItem(
               org.hamcrest.Matchers.allOf(
                   org.hamcrest.Matchers.hasProperty("name", org.hamcrest.Matchers.is("Basil")),
                   org.hamcrest.Matchers.hasProperty("status", org.hamcrest.Matchers.is("Healthy"))))));
    }

    @Test void FT15_unknownRouteReturns404() throws Exception {
        mvc.perform(get("/does-not-exist")).andExpect(status().isNotFound());
    }
}
