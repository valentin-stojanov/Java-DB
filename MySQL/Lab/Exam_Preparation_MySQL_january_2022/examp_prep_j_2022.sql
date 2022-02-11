CREATE DATABASE fsd;
USE fsd;

# 01. Table Design
CREATE TABLE `countries` (
`id` INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(45) NOT NULL
);

CREATE TABLE `towns` (
`id` INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(45) NOT NULL,
`country_id` INT NOT NULL,
CONSTRAINT  `fk_towns_countries`
FOREIGN KEY (`country_id`)
REFERENCES `countries`(`id`)
);

CREATE TABLE `stadiums` (
`id` INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(45) NOT NULL,
`capacity` INT NOT NULL,
`town_id` INT NOT NULL,
CONSTRAINT  `fk_stadiums_towns`
FOREIGN KEY (`town_id`)
REFERENCES `towns`(`id`)
);

CREATE TABLE `teams`(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(45) NOT NULL,
`established` DATE NOT NULL,
`fan_base` BIGINT(20) NOT NULL DEFAULT 0,
`stadium_id` INT NOT NULL,
CONSTRAINT  `fk_teams_stadiums`
FOREIGN KEY (`stadium_id`)
REFERENCES `stadiums`(`id`)
);

CREATE TABLE `skills_data`(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`dribbling` INT DEFAULT 0,
`pace` INT DEFAULT 0,
`passing` INT DEFAULT 0,
`shooting` INT DEFAULT 0,
`speed` INT DEFAULT 0,
`strength` INT DEFAULT 0
);

CREATE TABLE `coaches`(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`first_name` VARCHAR(10) NOT NULL,
`last_name` VARCHAR(20) NOT NULL,
`salary` DECIMAL(10,2) DEFAULT 0,
`coach_level` INT NOT NULL DEFAULT 0
);

CREATE TABLE `players`(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`first_name` VARCHAR(10) NOT NULL,
`last_name` VARCHAR(20) NOT NULL,
`age` INT NOT NULL DEFAULT 0,
`position` CHAR(1) NOT NULL,
`salary` DECIMAL(10,2) NOT NULL DEFAULT 0,
`hire_date` DATE,
`skills_data_id` INT NOT NULL,
`team_id` INT,
CONSTRAINT `fk_players_skills`
FOREIGN KEY (`skills_data_id`)
REFERENCES `skills_data`(`id`),
CONSTRAINT `fk_players_teams`
FOREIGN KEY (`team_id`)
REFERENCES `teams`(`id`)
);

CREATE TABLE `players_coaches`(
`player_id` INT,
`coach_id` INT,
CONSTRAINT `fk_players_coaches_players`
FOREIGN KEY (`player_id`)
REFERENCES `players`(`id`),
CONSTRAINT `fk_players_coaches_coaches`
FOREIGN KEY (`coach_id`)
REFERENCES `coaches`(`id`)
);

ALTER TABLE `players_coaches`
 ADD CONSTRAINT `PK_players_coaches_players`
PRIMARY KEY (`player_id`, `coach_id`);

# 02. Insert
INSERT INTO coaches (first_name, last_name, salary, coach_level)
SELECT first_name, last_name, salary*2, CHAR_LENGTH(first_name) AS coach_level
FROM players
WHERE age >= 45;

# 03. Update
UPDATE coaches AS c
JOIN players_coaches AS pc
ON c.id = pc.coach_id
SET coach_level = coach_level + 1
WHERE c.first_name LIKE 'A%';

# 04. Delete
DELETE
FROM players
WHERE age >= 45;

# 05. Players
SELECT `first_name`, `age`, `salary`
FROM `players`
ORDER BY `salary` DESC;

# 06. Young offense players without contract
SELECT 
	p.`id`,
	concat_ws(' ', p.`first_name`, p.`last_name`) AS `full_name`,
    p.`age`,
    p.`position`,
    p.`hire_date`
FROM `players` AS p
JOIN `skills_data` AS sd
ON p.`skills_data_id` = sd.`id`
WHERE p.`age` < 23
	AND p.`position` = 'A'
    AND p.`hire_date` IS NULL
    AND sd.`strength` > 50
ORDER BY p.`salary` ASC, p.`age`;

# 07. Detail info for all team

SELECT 
    ANY_VALUE(t.name),
    t.established,
    t.fan_base,
    COUNT(p.id) AS 'count'
FROM
    teams AS t
        LEFT JOIN
    players AS p ON p.team_id = t.id
GROUP BY t.id
ORDER BY `count` DESC , t.fan_base DESC;

#8. The fastest player by towns

SELECT 
    MAX(sd.`speed`) AS max_speed, tw.`name`
FROM
    `skills_data` AS sd
        RIGHT JOIN
    `players` AS p ON p.`skills_data_id` = sd.`id`
        RIGHT JOIN
    `teams` AS tm ON p.`team_id` = tm.`id`
        RIGHT JOIN
    `stadiums` AS s ON tm.`stadium_id` = s.`id`
        RIGHT JOIN
    `towns` AS tw ON s.`town_id` = tw.`id`
WHERE
    tm.`name` != 'Devify'
GROUP BY tw.`id`
ORDER BY max_speed DESC , tw.name ASC;

# 09. Total salaries and players by country

SELECT 
    c.`name`,
    COUNT(p.id) AS total_count_of_players,
    SUM(p.salary) AS total_sum_of_salaries
FROM
    `countries` AS c
        LEFT JOIN
    `towns` AS tw ON tw.`country_id` = c.`id`
        LEFT JOIN
    `stadiums` AS st ON st.`town_id` = tw.`id`
        LEFT JOIN
    `teams` AS tm ON tm.`stadium_id` = st.`id`
        LEFT JOIN
    `players` AS p ON p.`team_id` = tm.`id`
GROUP BY c.`id`
ORDER BY total_count_of_players DESC , c.`name`;

