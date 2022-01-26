USE `camp`;

# 1. Mountains and Peaks
CREATE TABLE `mountains`(
	`id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50)
);

CREATE TABLE `peaks`(
	`id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50),
    `mountain_id` INT,
    CONSTRAINT `fk_peaks_mountains`
    FOREIGN KEY (`mountain_id`)
    REFERENCES `mountains`(`id`)
);

# 2. Trip Organization
SELECT 
    `driver_id`,
    `vehicle_type`,
    CONCAT(`first_name`, ' ', `last_name`) AS `driver_name`
FROM
    `campers` AS c
        JOIN
    `vehicles` AS v ON v.`driver_id` = c.`id`;

# 3. SoftUni Hiking
SELECT 
    `starting_point`,
    `end_point`,
    `leader_id`,
    CONCAT(`first_name`, ' ', `last_name`) AS `leader_name`
FROM
    `routes` AS r
        JOIN
    `campers` AS c ON r.`leader_id` = c.`id`;
