-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.1.0 - MySQL Community Server - GPL
-- Server OS:                    Linux
-- HeidiSQL Version:             12.5.0.6677
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for test
CREATE DATABASE IF NOT EXISTS `test` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `test`;

-- Dumping structure for table test.authorities
CREATE TABLE IF NOT EXISTS `authorities` (
                                             `id` bigint NOT NULL AUTO_INCREMENT,
                                             `name` varchar(50) NOT NULL,
    `api_pattern` varchar(50) NOT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table test.authorities: ~2 rows (approximately)
INSERT INTO `authorities` (`id`, `name`, `api_pattern`) VALUES
                                                            (1, 'READ_PRIVILEGE', '/api/v1/users'),
                                                            (2, 'WRITE_PRIVILEGE', '/api/v1/profile');

-- Dumping structure for table test.refresh_token
CREATE TABLE IF NOT EXISTS `refresh_token` (
                                               `id` int NOT NULL AUTO_INCREMENT,
                                               `token` varchar(255) NOT NULL,
    `expiry_date` timestamp NOT NULL,
    `user_id` bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `user_id` (`user_id`),
    CONSTRAINT `refresh_token_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table test.refresh_token: ~1 rows (approximately)
INSERT INTO `refresh_token` (`id`, `token`, `expiry_date`, `user_id`) VALUES
    (1, 'dadc1611-b62e-49a4-89b8-eaebfc50bc2b', '2024-06-06 13:49:58', 1);

-- Dumping structure for table test.roles
CREATE TABLE IF NOT EXISTS `roles` (
                                       `id` bigint NOT NULL AUTO_INCREMENT,
                                       `name` varchar(50) NOT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table test.roles: ~2 rows (approximately)
INSERT INTO `roles` (`id`, `name`) VALUES
                                       (1, 'ROLE_ADMIN'),
                                       (2, 'ROLE_USER');

-- Dumping structure for table test.role_authorities
CREATE TABLE IF NOT EXISTS `role_authorities` (
                                                  `role_id` bigint NOT NULL,
                                                  `authority_id` bigint NOT NULL,
                                                  PRIMARY KEY (`role_id`,`authority_id`),
    KEY `authority_id` (`authority_id`),
    CONSTRAINT `role_authorities_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE,
    CONSTRAINT `role_authorities_ibfk_2` FOREIGN KEY (`authority_id`) REFERENCES `authorities` (`id`) ON DELETE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table test.role_authorities: ~3 rows (approximately)
INSERT INTO `role_authorities` (`role_id`, `authority_id`) VALUES
                                                               (1, 1),
                                                               (2, 1),
                                                               (1, 2);

-- Dumping structure for table test.users
CREATE TABLE IF NOT EXISTS `users` (
                                       `id` bigint NOT NULL AUTO_INCREMENT,
                                       `username` varchar(50) NOT NULL,
    `password` varchar(255) NOT NULL,
    `enabled` tinyint(1) NOT NULL DEFAULT '1',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table test.users: ~2 rows (approximately)
INSERT INTO `users` (`id`, `username`, `password`, `enabled`) VALUES
                                                                  (1, 'admin', '$2a$10$M3fKYEe/nCzhLduKTm1juugBjZ7087YC8je.UpjhtclumOSH2JYzG', 1),
                                                                  (2, 'user', '$2a$10$D9QvY5Yq8y1d2a4F9h1F.e3Gv7F3p7OH9T/5XG2GIb7u1z/ZZQG.K', 1);

-- Dumping structure for table test.user_authorities
CREATE TABLE IF NOT EXISTS `user_authorities` (
                                                  `user_id` bigint NOT NULL,
                                                  `authority_id` bigint NOT NULL,
                                                  PRIMARY KEY (`user_id`,`authority_id`),
    KEY `authority_id` (`authority_id`),
    CONSTRAINT `user_authorities_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
    CONSTRAINT `user_authorities_ibfk_2` FOREIGN KEY (`authority_id`) REFERENCES `authorities` (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table test.user_authorities: ~2 rows (approximately)
INSERT INTO `user_authorities` (`user_id`, `authority_id`) VALUES
                                                               (1, 1),
                                                               (2, 2);

-- Dumping structure for table test.user_roles
CREATE TABLE IF NOT EXISTS `user_roles` (
                                            `user_id` bigint NOT NULL,
                                            `role_id` bigint NOT NULL,
                                            PRIMARY KEY (`user_id`,`role_id`) USING BTREE,
    KEY `role_id` (`role_id`) USING BTREE,
    CONSTRAINT `user_roles_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table test.user_roles: ~2 rows (approximately)
INSERT INTO `user_roles` (`user_id`, `role_id`) VALUES
                                                    (1, 1),
                                                    (2, 2);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
