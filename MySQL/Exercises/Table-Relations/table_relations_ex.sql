CREATE DATABASE `table_ralations`;
USE `table_ralations`;

# 01. One-To-One Relationship
CREATE TABLE `passports`(
	`passport_id` INT PRIMARY KEY AUTO_INCREMENT,
    `passport_number` VARCHAR(50) UNIQUE
) AUTO_INCREMENT = 101;

INSERT INTO `passports`(`passport_number`)
VALUES
('N34FG21B'),
('K65LO4R7'),
('ZE657QP2');

CREATE TABLE `people`(
	`person_id` INT PRIMARY KEY AUTO_INCREMENT,
    `first_name` VARCHAR(50),
    `salary` DECIMAL(10,2),
    `passport_id` INT UNIQUE,
    CONSTRAINT `fk_people_passports`
    FOREIGN KEY (`passport_id`)
    REFERENCES `passports`(`passport_id`)    
);

INSERT INTO `people`(`first_name`, `salary`, `passport_id`)
VALUES
('Roberto', 43300.00, 102),
('Tom', 56100.00, 103),
('Yana', 60200.00, 101);

# 02. One-To-Many Relationship
CREATE TABLE `manufacturers`(
	`manufacturer_id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(20),
    `established_on` DATE
);

INSERT INTO `manufacturers`(`name`, `established_on`)
VALUES
('BMW', '1916-03-01'),
('Tesla', '2003-01-01'),
('Lada', '1966-05-01');

CREATE TABLE `models`(
	`model_id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(20),
    `manufacturer_id` INT,
    CONSTRAINT `fk_models_manufacturer`
    FOREIGN KEY (`manufacturer_id`)
    REFERENCES `manufacturers`(`manufacturer_id`)
)AUTO_INCREMENT = 101 ;

INSERT INTO `models`(`name`, `manufacturer_id`)
VALUES
('X1', 1),	
('i6', 1),	
('Model S', 2),	
('Model X', 2),	
('Model 3', 2),	
('Nova', 3);

# 3. Many-To-Many Relationship
CREATE TABLE `students`(
	`student_id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(30)    
);

INSERT INTO `students`(`name`)
VALUES
('Mila'),
('Toni'),
('Ron');

CREATE TABLE `exams`(
	`exam_id` INT PRIMARY KEY AUTO_INCREMENT,
	`name` VARCHAR(30)
) AUTO_INCREMENT=101;

INSERT INTO	`exams`(`name`)
VALUES
('Spring MVC'), 
('Neo4j'), 
('Oracle 11g'); 

CREATE TABLE `students_exams`(
	`student_id` INT NOT NULL,
    `exam_id` INT NOT NULL,
    CONSTRAINT pk_student_exam
    PRIMARY KEY (`student_id`, `exam_id`),
    CONSTRAINT fk_students_exam_student
    FOREIGN KEY (`student_id`)
    REFERENCES `students`(`student_id`),
	CONSTRAINT fk_students_exams_exams
	FOREIGN KEY (`exam_id`)
	REFERENCES `exams`(`exam_id`)
);

INSERT INTO `students_exams`
VALUES
(1, 101),
(1, 102),
(2, 101),
(3, 103),
(2, 102),
(2, 103);