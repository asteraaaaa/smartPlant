package com.csc3700.smartplant.service;

import com.csc3700.smartplant.service.PumpClientService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Implementation of PumpClientService that sends HTTP requests to the Arduino.
 */
@Service
public class PumpClientServiceImpl implements PumpClientService {
    private final RestTemplate rest = new RestTemplate();

    /**
     * Instructs your Arduino to run the pump for the given duration (ms).
     * Update the IP/port to match your MCU's address.
     */
    @Override
    public void turnOnPump(long durationMs) {
        String url = "http://192.168.1.50/pump/on?duration=" + durationMs;
        rest.getForObject(url, Void.class);
    }
}
