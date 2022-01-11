USE `soft_uni`;

# 1. Find All Information About Departments 
SELECT *
FROM `departments`
ORDER BY `department_id`;

# 2. Find all Department Names
SELECT `name`
FROM `departments`
ORDER BY `department_id`;

# 3. Find salary of Each Employee
SELECT `first_name`, `last_name`, `salary`
FROM `employees`;

# 4. Find Full Name of Each Employee
SELECT `first_name`, `middle_name`, `last_name`
FROM `employees`
ORDER BY `employee_id`;

# 5. Find Email Address of Each Employee
SELECT CONCAT(`first_name`, '.', `last_name`, '@softuni.bg') AS `full_email_address`
FROM `employees`;

# 6. Find All Different Employee's Salaries
SELECT DISTINCT `salary`
FROM `employees`;

# 7. Find all Information About Employees
SELECT * FROM `employees`
WHERE `job_title` = 'Sales Representative'
ORDER BY `employee_id`;

# 8. Find Names of All Employees by salary in Range 
SELECT `first_name`, `last_name`, `job_title`
FROM `employees`
#WHERE `salary` >= 20000 AND `salary` <= 30000;
WHERE `salary` BETWEEN 20000 AND 30000;

# 9.  Find Names of All Employees
SELECT CONCAT_WS(' ', `first_name`, `middle_name`, `last_name`) AS `Full Name`
FROM `employees`
WHERE `salary` IN(25000, 14000, 12500, 23600);

# 10. Find All Employees Without Manager
SELECT `first_name`, `last_name`
FROM `employees`
WHERE `manager_id` IS NULL;

# 11. Find All Employees with salary More Than 50000
SELECT `first_name`, `last_name`, `salary`
FROM `employees`
WHERE `salary` > 50000
ORDER BY `salary` DESC;

# 12. Find 5 Best Paid Employees
SELECT `first_name`, `last_name`
FROM `employees`
ORDER BY `salary` DESC
LIMIT 5;

# 13. Find All Employees Except Marketing
SELECT `first_name`, `last_name`
FROM `employees`
WHERE `department_id` != 4;
