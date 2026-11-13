package com.csc3700.smartplant.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/** Default pump: does no I/O, only logs. Safe to run without hardware. */
@Service
@ConditionalOnProperty(name = "smartplant.pump.mode", havingValue = "mock", matchIfMissing = true)
public class MockPumpClientService implements PumpClientService {
    private static final Logger log = LoggerFactory.getLogger(MockPumpClientService.class);

    @Override
    public boolean turnOnPump(long durationMs) {
        log.info("[MOCK PUMP] running for {} ms", durationMs);
        return true;
    }
}
