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
DROP PRIMARY KEY;

ALTER TABLE `users`
ADD CONSTRAINT `pk_users`
PRIMARY KEY (`id`, `username`);

# 9.Set Default Value of a Field
ALTER TABLE `users`
CHANGE `last_login_time` `last_login_time` DATETIME DEFAULT NOW();

# 10.Set Unique Field
ALTER TABLE `users`
DROP PRIMARY KEY,
ADD PRIMARY KEY `pk_users` (`id`),
ADD CONSTRAINT `uq_username`
UNIQUE (`username`);

# 11.Movies Database
CREATE DATABASE `movies`;

USE `movies`;

CREATE TABLE `directors` (
	`id` INT PRIMARY KEY AUTO_INCREMENT,
    `director_name` VARCHAR(30) NOT NULL,
    `notes` TEXT
);

INSERT INTO `directors`(`id`,`director_name`, `notes`)
VALUES
('1','Pesho',NULL), 
('2','Ivan',NULL), 
('3','Gosho',NULL), 
('4','Tapata',NULL), 
('5','Ali',NULL);

CREATE TABLE `genres`(
	`id` INT AUTO_INCREMENT PRIMARY KEY,
    `genre_name` VARCHAR(200) NOT NULL,
    `notes` TEXT
);

INSERT INTO `genres` (`id`, `genre_name`, `notes`)
VALUES
('1','Parody',NULL),
('2','Comedy',NULL),
('3','Drama',NULL),
('4','Action',NULL),
('5','Animation',NULL);


CREATE TABLE `categories`(
	`id` INT AUTO_INCREMENT PRIMARY KEY,
    `category_name` VARCHAR(200) NOT NULL,
    `notes` TEXT
);

INSERT INTO `categories` (`id`, `category_name`, `notes`)
VALUES
('1','Parody',NULL),
('2','Comedy',NULL),
('3','Drama',NULL),
('4','Action',NULL),
('5','Animation',NULL);

CREATE TABLE `movies`(
	`id` INT AUTO_INCREMENT PRIMARY KEY,
    `title` VARCHAR(200) NOT NULL,
    `director_id` INT ,
    `copyright_year` DATE,
    `length` VARCHAR(20),
    `genre_id` INT ,
    `category_id` INT,
    `rating` FLOAT,
    `notes` TEXT
);

INSERT INTO `movies`(`id`, `title`, `director_id`, `copyright_year`,`length`, `genre_id`,`category_id`, `rating`, `notes`)
VALUES
('1', 'No comment', '1', NULL, NULL, 1,1, NULL, NULL),
('2', 'No comment', '2', NULL, NULL, 2,5, NULL, NULL),
('3', 'No comment', '3', NULL, NULL, 5,4, NULL, NULL),
('4', 'No comment', '4', NULL , NULL, 4, 3, NULL, NULL),
('5', 'No comment', '4', NULL , NULL, 3,2, NULL, NULL);

# 12.Car Rental Database
CREATE DATABASE `car_rental`;

USE `car_rental`;

CREATE TABLE `categories`(
	`id` INT PRIMARY KEY AUTO_INCREMENT,
	`category` VARCHAR(30) NOT NULL,
    `daily_rate` FLOAT,
    `weekly_rate` FLOAT,
    `monthly_rate` FLOAT,
    `weekend_rate` FLOAT    
);

INSERT INTO `categories`
VALUES
(1, 'test1', 11.11, 22.22, 30, 40),
(2, 'test2', 11.11, 22.22, 30, 40),
(3, 'test3', 11.11, 22.22, 30, 40);

CREATE TABLE `cars`(
	`id` INT PRIMARY KEY AUTO_INCREMENT,
    `plate_number` VARCHAR(10) NOT NULL,
    `make` VARCHAR(50),
    `model` VARCHAR(50),
    `car_year` DATE,
    `category_id` INT,
    `doors` INT,
    `picture` BLOB,
    `car_condition` TEXT,
    `available` BOOLEAN
);

INSERT INTO `cars`
VALUES
(1, '123456', 'testMake', 'TestModel', NULL, 1, 2, NULL, NULL, TRUE),
(2, '123456', 'testMake', 'TestModel', NULL, 2, 2, NULL, NULL, TRUE),
(3, '123456', 'testMake', 'TestModel', NULL, 3, 2, NULL, NULL, TRUE);
CREATE TABLE `employees`(
	`id` INT PRIMARY KEY AUTO_INCREMENT,
    `first_name` VARCHAR(30) NOT NULL,
    `last_name` VARCHAR(30) NOT NULL,
    `title` VARCHAR(20),
    `notes` TEXT
);

INSERT INTO `employees`
VALUES
(1, 'Test', 'Testov', 'TestTitle', NULL),
(2, 'Test', 'Testov', 'TestTitle', NULL),
(3, 'Test', 'Testov', 'TestTitle', NULL);

CREATE TABLE `customers`(
	`id` INT AUTO_INCREMENT PRIMARY KEY,
    `driver_licence_number` INT NOT NULL,
    `full_name` VARCHAR(110) NOT NULL,
    `address` VARCHAR(110),
    `zip_code` INT,
    `notes` TEXT
);

INSERT INTO `customers`
VALUES
(1, 111111, 'tEST tESTOV', NULL, 10000, NULL),
(2, 111111, 'tEST tESTOV', NULL, 10000, NULL),
(3, 111111, 'tEST tESTOV', NULL, 10000, NULL);

CREATE TABLE `rental_orders`(
	`id` INT AUTO_INCREMENT PRIMARY KEY,
	`employee_id` INT ,
	`customer_id` INT ,
	`car_id` INT ,
	`car_condition` VARCHAR(110),
	`tank_level` VARCHAR(20),
	`kilometrage_start` INT,
	`kilometrage_end` INT,
	`total_kilometrage` INT,
	`start_date` DATE,
	`end_date` DATE,
	`total_days` INT,
	`rate_applied` FLOAT,
	`tax_rate` FLOAT,
	`order_status` VARCHAR(20),
	`notes` TEXT
);

INSERT INTO `rental_orders`
VALUES
(1, 1, 1, 1, 'GOOD', 'TESTLEVEL', 220000, 222000, 2000, NULL, NULL, 0, 1.1, 2.2, NULL, NULL),
(2, 2, 2, 2, 'GOOD', 'TESTLEVEL', 220000, 222000, 2000, NULL, NULL, 0, 1.1, 2.2, NULL, NULL),
(3, 3, 3, 3, 'GOOD', 'TESTLEVEL', 220000, 222000, 2000, NULL, NULL, 0, 1.1, 2.2, NULL, NULL);

# 13.Basic Insert
CREATE DATABASE `soft_uni`;

USE `soft_uni`;

CREATE TABLE `towns`(
	`id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(30) NOT NULL
);

CREATE TABLE `addresses`(
	`id` INT PRIMARY KEY AUTO_INCREMENT,
    `address_text` VARCHAR(100) NOT NULL,
    `town_id` INT NOT NULL,
    CONSTRAINT `fk_addresses_towns`
    FOREIGN KEY `addresses`(`town_id`) REFERENCES `towns`(`id`)
);

CREATE TABLE `departments`(
	`id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(20)
);

CREATE TABLE `employees`(
	`id` INT PRIMARY KEY AUTO_INCREMENT,
    `first_name` VARCHAR(30) NOT NULL,
    `middle_name` VARCHAR(30) NOT NULL,
    `last_name` VARCHAR(30) NOT NULL,
    `job_title` VARCHAR(20),
    `salary` DECIMAL,
	`department_id` INT,
    `hire_date` DATE,
    `address_id` INT,
    CONSTRAINT `fk_employees_departments`
    FOREIGN KEY `employees`(`department_id`) REFERENCES `departments`(`id`),
    CONSTRAINT `fk_employees_addresses`
    FOREIGN KEY `employees`(`address_id`) REFERENCES `addresses`(`id`)
);

INSERT INTO `towns`
VALUES
(1, 'Sofia'),
(2, 'Plovdiv'),
(3, 'Varna'),
(4, 'Burgas');

INSERT INTO `departments`
VALUES
(1, 'Engineering'),
(2, 'Sales'),
(3, 'Marketing'),
(4, 'Software Development'),
(5, 'Quality Assurance');

INSERT INTO `employees` (`id`, `first_name`, `middle_name`, `last_name`, `job_title`, `department_id`, `hire_date`, `salary`)
VALUES
(1, 'Ivan', 'Ivanov', 'Ivanov', '.NET Developer', 4, '2013-02-01', 3500.00),
(2, 'Petar', 'Petrov', 'Petrov', 'Senior Engineer', 1, '2004-03-02', 4000.00),
(3, 'Maria','Petrova', 'Ivanova', 'Intern', 5, '2016-08-28', 525.25),
(4, 'Georgi', 'Terziev', 'Ivanov', 'CEO', 2, '2007-12-09', 3000.00),
(5, 'Peter', 'Pan', 'Pan', 'Intern', 3, '2016-08-28', 599.88);

# 14.Basic Select All Fields
SELECT *
FROM `towns`;

SELECT *
FROM `departments`;

SELECT *
FROM `employees`;

# 15.Basic Select All Fields and Order Them
SELECT *
FROM `towns`
ORDER BY `name` ASC;

SELECT *
FROM `departments`
ORDER BY `name` ASC;

SELECT *
FROM `employees`
ORDER BY `salary` DESC;

# 16.	Basic Select Some Fields
SELECT `name`
FROM `towns`
ORDER BY `name` ASC;

SELECT `name`
FROM `departments`
ORDER BY `name` ASC;

SELECT `first_name`, `last_name`, `job_title`, `salary`
FROM `employees`
ORDER BY `salary` DESC;

# 17.Increase Employees Salary
UPDATE `employees`
SET `salary` = `salary` * 1.1;

SELECT `salary`
FROM `employees`;

# 18.Delete All Records
TRUNCATE `occupancies`