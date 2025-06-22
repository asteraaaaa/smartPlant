package com.csc3700.smartplant.service;

/**
 * Interface for controlling the water pump on the Arduino via HTTP.
 */
public interface PumpClientService {
    /**
     * Runs the pump for the specified duration in milliseconds.
     * @param durationMs duration to run the pump (ms)
     */
    void turnOnPump(long durationMs);
}
