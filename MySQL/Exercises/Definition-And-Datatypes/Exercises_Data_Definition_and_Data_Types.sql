CREATE DATABASE `minions`;
USE `minions`;
    
# 1.	Create Tables
CREATE TABLE `minions`(
	`id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `age` INT
);

CREATE TABLE `towns`(
	`town_id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL    
);

# 2.	Alter Minions Table
ALTER TABLE `towns`
CHANGE `town_id` `id` INT AUTO_INCREMENT;

ALTER TABLE `minions`
ADD COLUMN `town_id` INT, 
ADD CONSTRAINT fk_minions_towns
FOREIGN KEY `minions`(`town_id`)
REFERENCES `towns`(`id`);

# 3.	Insert Records in Both Tables

INSERT INTO `towns`
VALUES
(1, 'Sofia'),
(2, 'Plovdiv'),
(3, 'Varna');

INSERT INTO `minions`
VALUES
(1, 'Kevin', 22, 1),
(2, 'Bob', 15, 3),
(3, 'Steward', NULL, 2);

# 4.	Truncate Table Minions
TRUNCATE TABLE `minions`;

# 5.	Drop All Tables
DROP TABLE `minions`;
DROP TABLE `towns`;

# 6.	Create Table People

CREATE TABLE `people`(
	`id` INT PRIMARY KEY AUTO_INCREMENT UNIQUE,
    `name` VARCHAR(200) NOT NULL,
    `picture` BLOB,
    `height` FLOAT(4,2),
    `weight` FLOAT(4,2),
    `gender` CHAR(1) NOT NULL,
    `birthdate` DATE NOT NULL,
    `biography` TEXT
);

INSERT INTO `people`
VALUES
(1, 'Test', NULL, 1.75, 72.5, 'm', '1991-08-13', 'aergeargaegegerg'),
(2, 'Test', NULL, 1.75, 72.5, 'm', '1991-08-13', 'aergeargaegegerg'),
(3, 'Test', NULL, 1.75, 72.5, 'm', '1991-08-13', 'aergeargaegegerg'),
(4, 'Test', NULL, 1.75, 72.5, 'm', '1991-08-13', 'aergeargaegegerg'),
(5, 'Test', NULL, 1.75, 72.5, 'm', '1991-08-13', 'aergeargaegegerg');

# 7.	Create Table Users
CREATE TABLE `users`(
`id` INT PRIMARY KEY AUTO_INCREMENT UNIQUE,
`username` VARCHAR(30) UNIQUE,
`password` VARCHAR(26) NOT NULL,
`profile_picture` BLOB,
`last_login_time` DATETIME,
`is_deleted` BOOLEAN
);

INSERT INTO `users`
VALUES
(1, 'tEST1', 'TESTpASS', NULL, NOW(), FALSE),
(2, 'tES2T', 'TESTpASS', NULL, NOW(), FALSE),
(3, 'tE3ST', 'TESTpASS', NULL, NOW(), FALSE),
(4, 't4EST', 'TESTpASS', NULL, NOW(), FALSE),
(5, '5tEST', 'TESTpASS', NULL, NOW(), FALSE);

# 8.Change Primary Key
ALTER TABLE `users`
DROP PRIMARY KEY,
ADD CONSTRAINT `pk_users`
PRIMARY KEY (`id`, `username`);

# 9.Set Default Value of a Field
ALTER TABLE `users`
CHANGE `last_login_time` `last_login_time` DATETIME DEFAULT NOW();