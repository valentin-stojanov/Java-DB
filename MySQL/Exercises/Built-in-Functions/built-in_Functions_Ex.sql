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

