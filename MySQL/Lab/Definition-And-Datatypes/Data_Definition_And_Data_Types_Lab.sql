CREATE DATABASE `gamebar`;
USE `gamebar`;

# 1. Create Tables
CREATE TABLE `employees` (
	`id` INT AUTO_INCREMENT PRIMARY KEY,
    `first_name` VARCHAR(50) NOT NULL,
    `last_name` VARCHAR(50) NOT NULL
);

CREATE TABLE `categories`(
	`id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(30) NOT NULL
);

CREATE TABLE `products`(
	`id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(30) NOT NULL,
    `category_id` INT
);

# 2. Insert Data in Tables
INSERT INTO `employees`
VALUES
(1, 'Test1', 'Test1'),
(2, 'Test2', 'Test2'),
(3, 'Test3', 'Test3');

SELECT * FROM `employees`;

# 3. Alter Tables
ALTER TABLE `employees`
ADD COLUMN `middle_name` VARCHAR(30);

# 4. Adding Constraints
ALTER TABLE `products`
ADD CONSTRAINT fk_products_categories
FOREIGN KEY `products`(`category_id`) REFERENCES `categories`(`id`);

# 5. Modifying Columns
ALTER TABLE `employees`
MODIFY COLUMN `middle_name` VARCHAR(100);