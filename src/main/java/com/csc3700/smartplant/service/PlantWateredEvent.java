package com.csc3700.smartplant.service;

/** Published after the pump successfully waters a plant. */
public record PlantWateredEvent(Long plantId) {}
