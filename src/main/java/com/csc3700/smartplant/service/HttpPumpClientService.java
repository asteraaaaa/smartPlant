package com.csc3700.smartplant.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/** Real pump: sends an HTTP request to the Arduino. Enable with smartplant.pump.mode=http. */
@Service
@ConditionalOnProperty(name = "smartplant.pump.mode", havingValue = "http")
public class HttpPumpClientService implements PumpClientService {
    private static final Logger log = LoggerFactory.getLogger(HttpPumpClientService.class);

    private final RestTemplate rest;
    private final String url;

    public HttpPumpClientService(RestTemplateBuilder builder,
                                 @Value("${smartplant.pump.url}") String url,
                                 @Value("${smartplant.pump.timeout-ms}") long timeoutMs) {
        this.rest = builder.connectTimeout(Duration.ofMillis(timeoutMs))
                .readTimeout(Duration.ofMillis(timeoutMs)).build();
        this.url = url;
    }

    @Override
    public boolean turnOnPump(long durationMs) {
        try {
            rest.getForObject(url + "?duration=" + durationMs, String.class);
            return true;
        } catch (Exception e) {
            log.warn("Pump unreachable at {}: {}", url, e.getMessage());
            return false;
        }
    }
}
