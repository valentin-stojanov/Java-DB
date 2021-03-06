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

# 3. Town Names Starting With
DELIMITER $$
CREATE PROCEDURE usp_get_towns_starting_with(str TEXT)
	BEGIN
		SELECT `name`
		FROM `towns`
		WHERE `name` LIKE CONCAT(str, '%')
        ORDER BY `name`;
	END$$
DELIMITER ;

CALL usp_get_towns_starting_with('B');

# 4. Employees from Town
DELIMITER $$
CREATE PROCEDURE usp_get_employees_from_town(town_name VARCHAR(50))
	BEGIN
		SELECT e.`first_name`, e.`last_name`
		FROM `employees` AS e
		JOIN `addresses` AS a
		ON e.`address_id` = a.`address_id`
		JOIN `towns` AS t
		ON a.`town_id` = t.`town_id`
		WHERE t.`name` = town_name
		ORDER BY e.`first_name`, e.`last_name`, e.`employee_id`;
	END$$
DELIMITER ;

CALL usp_get_employees_from_town('Sofia');

# 5. Salary Level Function
DELIMITER $$
CREATE FUNCTION ufn_get_salary_level(salary DOUBLE)
RETURNS VARCHAR(7)
DETERMINISTIC
	BEGIN
		DECLARE salary_level VARCHAR(7);
        SET salary_level := (
			CASE 
				WHEN salary < 30000 THEN 'Low'
				WHEN salary <= 50000 THEN 'Average'
                ELSE 'High'
			END
        );
        RETURN salary_level;
    END$$
    DELIMITER ;
    
    SELECT ufn_get_salary_level(50000); -- TEST INPUT
    DROP FUNCTION ufn_get_salary_level;
	
# 06. Employees by Salary Level
DELIMITER $$
CREATE FUNCTION udf_get_condition(salary_level VARCHAR(7))
RETURNS TEXT
DETERMINISTIC
BEGIN
	RETURN (CASE 
		WHEN salary_level = 'Low' THEN "`salary` < 30000"
        WHEN salary_level = 'Average' THEN "30000 <= `salary` and `salary` <= 50000"
        ELSE "`salary` > 50000"
    END);
END$$

CREATE PROCEDURE usp_get_employees_by_salary_level(salary_level VARCHAR(7))
	BEGIN
    SET @cond = udf_get_condition(salary_level);
    SET @str = CONCAT('
		SELECT `first_name`, `last_name`
		FROM `employees`
		WHERE ', @cond, '
        ORDER BY `first_name` DESC, `last_name` DESC');              
        PREPARE stm FROM @str;
        EXECUTE stm;
        DEALLOCATE PREPARE stm;
	END$$
    
DELIMITER ;

SELECT udf_get_condition('High'); -- just for test
CALL usp_get_employees_by_salary_level('High');
DROP PROCEDURE usp_get_employees_by_salary_level;
DROP FUNCTION udf_get_condition;

/* 
(
IF( salary_level = 'High',`salary` > 50000, IF(salary_level = 'Average', 30000 <= `salary` and `salary` <= 50000, IF(salary_level = 'Low', `salary` < 30000, NULL)))
)
*/ 
DELIMITER $$
CREATE PROCEDURE usp_get_employees_by_salary_level(salary_level VARCHAR(7))
	BEGIN
		SELECT `first_name`, `last_name`
		FROM `employees`
		WHERE (
			IF( salary_level = 'High',`salary` > 50000, IF(salary_level = 'Average', 30000 <= `salary` and `salary` <= 50000, IF(salary_level = 'Low', `salary` < 30000, NULL)))
			)
        ORDER BY `first_name` DESC, `last_name` DESC;          
	END$$
DELIMITER ;

# 07. Define Function

DELIMITER $$
CREATE FUNCTION ufn_is_word_comprised(set_of_letters varchar(50), word varchar(50)) 
RETURNS INT
DETERMINISTIC
BEGIN
	RETURN REGEXP_LIKE(word, CONCAT('^[', set_of_letters, ']*$'));
END$$
DELIMITER ;

SELECT ufn_is_word_comprised('oistmiahf', 'halves');


# 08. Find Full Name
DELIMITER $$
CREATE PROCEDURE usp_get_holders_full_name()
BEGIN
SELECT CONCAT_WS(' ', `first_name`, `last_name`) AS 'full_name'
FROM `account_holders`
ORDER BY `full_name`;
END$$
DELIMITER ;

CALL usp_get_holders_full_name();
DROP PROCEDURE usp_get_holders_full_name;

# 9. People with Balance Higher Than
DELIMITER $$
CREATE PROCEDURE usp_get_holders_with_balance_higher_than(num DOUBLE)
BEGIN
	SELECT `first_name`, `last_name`
	FROM `account_holders` AS ah
	JOIN `accounts` AS a
	ON ah.`id` = a.`account_holder_id`
	GROUP BY a.`account_holder_id`
	HAVING sum(a.`balance`) > num
	ORDER BY a.`account_holder_id`;
END$$
DELIMITER ;
CALL usp_get_holders_with_balance_higher_than(7000);
DROP PROCEDURE usp_get_holders_with_balance_higher_than;

# 10. Future Value Function
DELIMITER $$
CREATE FUNCTION ufn_calculate_future_value(initial_sum DECIMAL(19,4), yearly_interest_rate DECIMAL(19,4), number_of_years INT)
RETURNS DECIMAL(19, 4)
DETERMINISTIC
BEGIN
	RETURN initial_sum * POW((1+yearly_interest_rate), number_of_years);
END$$
DELIMITER ;

SELECT ufn_calculate_future_value(1000.55, 0.08, 5);
DROP FUNCTION ufn_calculate_future_value;

# 11. Calculating Interest
DELIMITER $$
CREATE PROCEDURE usp_calculate_future_value_for_account(ac_id INT, interest_rate DECIMAL(19,4))
BEGIN
	SELECT 
		a.`id`,
		ah.`first_name`, 
		ah.`last_name`, 
		a.`balance` AS `current_balance`,
		ufn_calculate_future_value(a.`balance`, interest_rate, 5) AS `balance_in5_year`    
	FROM `accounts` AS a
	JOIN `account_holders` AS ah
	ON a.`account_holder_id` = ah.`id`
	WHERE a.`id` = ac_id;
END$$
DELIMITER ;

CALL usp_calculate_future_value_for_account(1, 0.1);
DROP PROCEDURE usp_calculate_future_value_for_account;