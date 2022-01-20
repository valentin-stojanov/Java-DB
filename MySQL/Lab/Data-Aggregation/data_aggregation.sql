USE `restaurant`;

# 1.Departments Info
SELECT 
	`department_id`,
    count(*) AS `Number of Employees`
FROM `employees`
GROUP BY `department_id`
ORDER BY `department_id`;