-- Users
INSERT INTO users (username, email, password, created_at) VALUES
    ('emily_j', 'emily.j@example.com', 'hashed_pass123', '2025-06-15 09:30:45'),
    ('carlos_m', 'carlos.m@example.com', 'secure_hash456', '2025-06-16 14:20:31'),
    ('priya_k', 'priya.k@example.com', 'p@ssw0rd!789', '2025-06-17 11:45:22'),
    ('tom_s', 'tom.s@example.com', 'tom_hash_2025', '2025-06-18 16:33:18'),
    ('li_wei', 'li.w@example.com', 'liwei_pwd_secure', '2025-06-19 08:05:57');

-- Plants
INSERT INTO plants (name, type, optimal_moisture_level, optimal_temperature, optimal_humidity) VALUES
    ('Desert Rose', 'SUCCULENT', 15.0, 28.0, 30.0),
    ('Monstera Deliciosa', 'TROPICAL', 45.0, 24.0, 70.0),
    ('Basil', 'HERB', 60.0, 22.0, 50.0),
    ('Orchid Phalaenopsis', 'EPIPHYTE', 40.0, 25.0, 80.0),
    ('Snake Plant', 'SANSEVIERIA', 20.0, 23.0, 40.0);

    -- Sensors
INSERT INTO sensors (plant_id, soil_moisture, temperature, humidity, recorded_at) VALUES
    (1, 14.2, 28.5, 32.1, '2025-06-20 10:00:00'),
    (1, 15.8, 29.1, 31.7, '2025-06-20 15:30:00'),
    (2, 43.7, 23.8, 68.9, '2025-06-20 11:45:00'),
    (2, 46.2, 24.3, 72.4, '2025-06-20 17:20:00'),
    (3, 58.9, 21.5, 49.3, '2025-06-20 09:15:00'),
    (3, 62.4, 22.1, 51.7, '2025-06-20 14:40:00'),
    (4, 38.5, 24.9, 79.2, '2025-06-20 08:30:00'),
    (4, 41.3, 25.4, 82.1, '2025-06-20 16:10:00'),
    (5, 19.1, 23.2, 38.7, '2025-06-20 13:25:00'),
    (5, 21.5, 22.8, 41.3, '2025-06-20 19:05:00');

    -- System Logs
INSERT INTO system_logs (event_type, description, created_at) VALUES
    ('SYSTEM_START', 'Application boot completed', '2025-06-21 08:00:12'),
    ('USER_LOGIN', 'User emily_j logged in', '2025-06-21 08:15:33'),
    ('WATERING_TRIGGERED', 'Zone 3 watering activated', '2025-06-21 10:30:47'),
    ('SENSOR_ERROR', 'Moisture sensor 8 calibration needed', '2025-06-21 14:22:19'),
    ('BACKUP_COMPLETE', 'Nightly database backup successful', '2025-06-21 02:45:56');

-- FIXED: Watering Schedule (using seconds instead of time strings)
INSERT INTO watering_schedule (plant_id, user_id, schedule_time, duration) VALUES
    (2, 3, '2025-06-22 07:00:00', 900),   -- 15 minutes = 900 seconds
    (3, 1, '2025-06-22 18:30:00', 600),   -- 10 minutes = 600 seconds
    (1, 4, '2025-06-23 06:45:00', 300),   -- 5 minutes = 300 seconds
    (4, 2, '2025-06-23 08:15:00', 720),   -- 12 minutes = 720 seconds
    (5, 5, '2025-06-24 09:00:00', 480);   -- 8 minutes = 480 seconds