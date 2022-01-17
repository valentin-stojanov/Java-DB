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
