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

INSERT INTO `towns` (`name`)
VALUES
('Sofia'),
('Plovdiv'),
('Varna');

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
	`id` INT PRIMARY KEY AUTO_INCREMENT,
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
