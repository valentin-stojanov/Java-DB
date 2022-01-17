# 01. Find Names of All Employees by First Name
SELECT `first_name`, `last_name`
FROM `employees`
WHERE `first_name` LIKE 'Sa%'
# WHERE left(`first_name`, 2) = 'Sa'
# WHERE substring(`first_name`, 1, 2) = 'Sa'
ORDER BY `employee_id`;

