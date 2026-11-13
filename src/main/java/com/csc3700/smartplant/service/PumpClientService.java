package com.csc3700.smartplant.service;

/**
 * Controls the water pump. Implementations: a mock (default) and an HTTP client for the Arduino.
 */
public interface PumpClientService {
    /**
     * Runs the pump for the given duration.
     * @return true if the pump accepted the command, false if it could not be reached
     */
    boolean turnOnPump(long durationMs);
}
