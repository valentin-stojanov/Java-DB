# SoftUni Database
# 01. Find Names of All Employees by First Name
SELECT `first_name`, `last_name`
FROM `employees`
WHERE `first_name` LIKE 'Sa%'
# WHERE left(`first_name`, 2) = 'Sa'
# WHERE substring(`first_name`, 1, 2) = 'Sa'
ORDER BY `employee_id`;

# 02.Find Names of All Employees by Last Name
SELECT `first_name`, `last_name`
FROM `employees`
WHERE `last_name` LIKE '%ei%'
ORDER BY `employee_id`;

# 03.Find First Names of All Employees.
SELECT `first_name`
FROM `employees`
WHERE `department_id` IN (3, 10) 
AND YEAR(`hire_date`) BETWEEN 1995 AND 2005
ORDER BY `employee_id`;

# 4.Find All Employees Except Engineers
SELECT `first_name`, `last_name`
FROM `employees`
WHERE `job_title` NOT LIKE '%engineer%'
ORDER BY `employee_id`;

# 5.Find Towns with Name Length
SELECT `name`
FROM `towns`
WHERE character_length(`name`) IN(5, 6)
ORDER BY `name`;

# 6.Find Towns Starting With
SELECT `town_id`, `name`
FROM `towns`
WHERE left(`name`, 1) IN('m', 'k', 'b', 'e')
ORDER BY `name`;

# 7.Find Towns Not Starting With
SELECT `town_id`, `name`
FROM `towns`
WHERE left(`name`, 1) NOT IN('R', 'b', 'd')
ORDER BY `name`;

# 8.Create View Employees Hired After 2000 Year
CREATE VIEW `v_employees_hired_after_2000` AS
SELECT `first_name`, `last_name`
FROM `employees`
WHERE year(`hire_date`) > 2000;

SELECT *
FROM `v_employees_hired_after_2000`;

# 9.Length of Last Name
SELECT `first_name`, `last_name`
FROM `employees`
WHERE char_length(`last_name`) = 5;

# Geography Database
# 10.Countries Holding 'A'
SELECT `country_name`, `iso_code`
FROM `countries`
WHERE `country_name` LIKE '%a%a%a%'
ORDER BY `iso_code`;

# 11.Mix of Peak and River Names
SELECT 
	`peak_name`, `river_name`,
    lower(concat(`peak_name`, substring(`river_name`, 2))) AS 'mix'
FROM 
	`peaks`,
    `rivers`
WHERE 
	right(`peak_name`, 1) = left(`river_name`, 1)
ORDER BY `mix`;

# Diablo Database
# 12.Games From 2011 and 2012 Year
SELECT `name`,  date_format(`start`, '%Y-%m-%d') AS `start`
FROM `games`
WHERE year(`start`) IN(2011, 2012)
ORDER BY `start`, `name`
LIMIT 50;

# 13.User Email Providers
SELECT 
	`user_name`, 
    substring(`email` ,locate('@', `email`) + 1) AS 'Email Provider'
FROM `users`
ORDER BY 
	`Email Provider`,
	`user_name`;

# 14.Get Users with IP Address Like Pattern
SELECT `user_name`, `ip_address`
FROM `users`
WHERE `ip_address` lIKE '___.1%.%.___'
ORDER BY `user_name`;

# 15. Show All Games with Duration
SELECT 
	`name`,
    (
		CASE
			WHEN HOUR(`start`) BETWEEN 0 AND 11 THEN 'Morning'
			WHEN HOUR(`start`) BETWEEN 12 AND 17 THEN 'Afternoon'
			WHEN HOUR(`start`) BETWEEN 17 AND 24 THEN 'Evening'
        END
    ) AS 'part_of_the_day',
    (
		CASE
			WHEN `duration` < 4 THEN 'Extra Short'
			WHEN `duration` < 7 THEN 'Short'			
			WHEN `duration` < 11 THEN 'Long'			
			ELSE 'Extra Long'			
        END
    ) AS 'Duration'
FROM `games`;
