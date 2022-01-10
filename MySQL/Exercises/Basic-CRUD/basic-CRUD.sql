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

# 5