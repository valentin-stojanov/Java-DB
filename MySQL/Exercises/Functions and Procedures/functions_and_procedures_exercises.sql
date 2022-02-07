# 1. Employees with Salary Above 35000
DELIMITER $$
CREATE PROCEDURE usp_get_employees_salary_above_35000()
BEGIN
	SELECT `first_name`, `last_name`
	FROM `employees`
	WHERE `salary` > 35000
	ORDER BY `first_name`, `last_name`;
END$$
DELIMITER ;

CALL usp_get_employees_salary_above_35000();

# 2. Employees with Salary Above Number
DELIMITER $$
CREATE PROCEDURE usp_get_employees_salary_above(num DECIMAL(19,4))
BEGIN
	SELECT `first_name`, `last_name`
	FROM `employees`
	WHERE `salary` >= num
    ORDER BY `first_name`, `last_name`, `employee_id`;
END$$
DELIMITER ;

CALL usp_get_employees_salary_above(45000);