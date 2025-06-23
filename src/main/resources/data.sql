INSERT INTO plants (id, name, min_optimal_moisture, max_optimal_moisture,
                    min_optimal_temperature, max_optimal_temperature,
                    min_optimal_humidity, max_optimal_humidity,
                    type, watering_frequency, last_watered)
VALUES
    (1, 'Demo Plant', 30, 60, 18, 30, 40, 70, 'SUCCULENT', 1, '2025-04-22'),
    (2, 'Basil', 50.0, 70.0, 20.0, 28.0, 40.0, 60.0, 'HERB', 1, '2025-06-22'),
    (3, 'Spider Plant', 45.0, 65.0, 16.0, 26.0, 35.0, 55.0, 'FLOWERING', 5, '2025-06-18');

INSERT INTO sensors (id, plant_id, soil_moisture, temperature, humidity, recorded_at)
VALUES
    (1, 1, 52.0, 25.3, 45.0, '2025-06-23 08:00:00'),
    (2, 2, 30.0, 27.8, 50.0, '2025-06-23 08:30:00'),
    (3, 3, 65.0, 24.1, 60.0, '2025-06-23 09:00:00');

INSERT INTO watering_schedule (id, plant_id, schedule_date, schedule_time, duration)
VALUES
    (1, 1, '2025-06-23', '2025-06-23 08:00:00', 'PT10M'),
    (2, 2, '2025-06-23', '2025-06-23 09:00:00', 'PT15M'),
    (3, 3, '2025-06-24', '2025-06-24 10:00:00', 'PT20M');
