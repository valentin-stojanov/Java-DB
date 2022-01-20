USE `restaurant`;

# 1.Departments Info
SELECT 
	`department_id`,
    count(*) AS `Number of Employees`
FROM `employees`
GROUP BY `department_id`
ORDER BY `department_id`;

# 2.Average Salary
SELECT
	`department_id`,
    round(avg(`salary`), 2) AS `Average salary`
FROM `employees`
GROUP BY `department_id`
ORDER BY `department_id`;

# 3.Min Salary
SELECT 
	`department_id`,
    round(min(`salary`), 2) AS `Min Salary`
FROM `employees`
GROUP BY `department_id`
HAVING `Min Salary` > 800
ORDER BY `department_id`;

