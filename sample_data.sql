-- Sample data for `users`
INSERT INTO `users` (`id`, `name`, `email`, `password`, `country`, `credits`, `verified_at`, `created_at`, `updated_at`) 
VALUES 
(1, 'John Doe', 'john.doe@example.com', 'password123', 'USA', 20, '2023-01-01 10:00:00', '2023-01-01 09:00:00', '2023-01-01 09:00:00'),
(2, 'Jane Smith', 'jane.smith@example.com', 'password456', 'Canada', 15, '2023-01-02 11:00:00', '2023-01-02 10:00:00', '2023-01-02 10:00:00');

-- Sample data for `schedules`
INSERT INTO `schedules` (`id`, `class_name`, `country`, `start_time`, `end_time`, `required_credits`, `total_slots`, `available_slots`) 
VALUES 
(1, 'Yoga Class', 'USA', '2023-12-01 08:00:00', '2023-12-01 09:00:00', 5, 10, 8),
(2, 'Pilates Class', 'Canada', '2023-12-02 10:00:00', '2023-12-02 11:00:00', 6, 15, 12);

-- Sample data for `bookings`
INSERT INTO `bookings` (`id`, `user_id`, `schedule_id`, `status`, `booking_time`, `created_at`) 
VALUES 
(1, 1, 1, 'BOOKED', '2023-11-20 10:00:00', '2023-11-20 10:00:00'),
(2, 2, 2, 'CANCELLED', '2023-11-21 11:00:00', '2023-11-21 11:00:00');

-- Sample data for `packages`
INSERT INTO `packages` (`id`, `name`, `country`, `credits`, `price`, `expires_in_days`) 
VALUES 
(1, 'Basic Plan', 'USA', 10, 19.99, 30),
(2, 'Premium Plan', 'Canada', 20, 29.99, 60);
