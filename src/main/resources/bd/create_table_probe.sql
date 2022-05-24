CREATE TABLE `probe` (
                         `id` bigint NOT NULL,
                         `direction` varchar(255) DEFAULT NULL,
                         `x` int DEFAULT NULL,
                         `y` int DEFAULT NULL,
                         `id_planet` bigint DEFAULT NULL,
                         `direction_in` varchar(255) DEFAULT NULL,
                         `x_in` int DEFAULT NULL,
                         `y_in` int DEFAULT NULL,
                         PRIMARY KEY (`id`),
                         KEY `FKsumq9brhhyaqo1cmr5hqk8q1g` (`id_planet`),
                         CONSTRAINT `FKsumq9brhhyaqo1cmr5hqk8q1g` FOREIGN KEY (`id_planet`) REFERENCES `planet` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
