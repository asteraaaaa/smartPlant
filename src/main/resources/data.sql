INSERT INTO plants (name, min_optimal_moisture, max_optimal_moisture,
                    min_optimal_temperature, max_optimal_temperature,
                    min_optimal_humidity, max_optimal_humidity,
                    type, watering_frequency, last_watered)
VALUES
    ('Demo Plant', 30, 60, 18, 30, 40, 70, 'SUCCULENT', 1, DATEADD('DAY', -1, CURRENT_DATE)),
    ('Basil', 50.0, 70.0, 20.0, 28.0, 40.0, 60.0, 'HERB', 1, DATEADD('DAY', -2, CURRENT_DATE)),
    ('Spider Plant', 45.0, 65.0, 16.0, 26.0, 35.0, 55.0, 'FLOWERING', 5, DATEADD('DAY', -3, CURRENT_DATE));

INSERT INTO sensors (plant_id, soil_moisture, temperature, humidity, recorded_at)
VALUES
    (1, 48.0, 25.0, 45.0, DATEADD('HOUR', -6, CURRENT_TIMESTAMP)),
    (1, 52.0, 25.3, 45.0, DATEADD('HOUR', -3, CURRENT_TIMESTAMP)),
    (1, 50.0, 26.1, 47.0, DATEADD('HOUR', -1, CURRENT_TIMESTAMP)),
    (2, 62.0, 26.8, 50.0, DATEADD('HOUR', -6, CURRENT_TIMESTAMP)),
    (2, 55.0, 27.8, 50.0, DATEADD('HOUR', -3, CURRENT_TIMESTAMP)),
    (2, 48.0, 27.0, 49.0, DATEADD('HOUR', -1, CURRENT_TIMESTAMP)),
    (3, 65.0, 24.1, 60.0, DATEADD('HOUR', -6, CURRENT_TIMESTAMP)),
    (3, 60.0, 24.5, 58.0, DATEADD('HOUR', -3, CURRENT_TIMESTAMP)),
    (3, 58.0, 24.1, 57.0, DATEADD('HOUR', -1, CURRENT_TIMESTAMP));

INSERT INTO watering_schedule (plant_id, schedule_date, schedule_time, duration, completed)
VALUES
    (1, CURRENT_DATE, DATEADD('HOUR', 1, CURRENT_TIMESTAMP), 'PT10M', FALSE),
    (2, CURRENT_DATE, DATEADD('HOUR', 2, CURRENT_TIMESTAMP), 'PT15M', FALSE),
    (3, DATEADD('DAY', 1, CURRENT_DATE), DATEADD('DAY', 1, CURRENT_TIMESTAMP), 'PT20M', FALSE);

INSERT INTO system_logs (event_type, description, created_at)
VALUES ('SYSTEM', 'SmartPlant started (mock pump)', CURRENT_TIMESTAMP);
