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
    round(avg(`salary`), 2)
FROM `employees`
GROUP BY `department_id`
ORDER BY `department_id`;
