/*Lab: Data Definition and Data Types*/

CREATE DATABASE `gamebar`;
USE `gamebar`;

/*1. Create Tables*/
CREATE TABLE `employees`(
	`id` INT PRIMARY KEY AUTO_INCREMENT,
    `first_name` VARCHAR(30) NOT NULL,
    `last_name` VARCHAR(30) NOT NULL
);

CREATE TABLE `categories`(
	`id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(40) NOT NULL
);

CREATE TABLE `products`(
	`id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(40) NOT NULL,
    `category_id` INT
);

/* 2. Insert Data in Tables */
INSERT INTO `employees`
VALUES
(1, 'Pesho', 'Peshov'),
(2, 'Gosho', 'Goshov'),
(3, 'Ivan', 'Ivanov');

SELECT * FROM `employees`;

/*3. Alter Tables*/
ALTER TABLE `employees`
ADD COLUMN `middle_name` VARCHAR(20);

/*4. Adding Constraints*/

ALTER TABLE `products`
ADD CONSTRAINT `fk_products_categories`
FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`);