# 1. Employee Address
SELECT 
    e.`employee_id`,
    e.`job_title`,
    a.`address_id`,
    a.`address_text`
FROM
    `employees` AS e
        JOIN
    `addresses` AS a ON e.`address_id` = a.`address_id`
ORDER BY a.`address_id` ASC
LIMIT 5;

# 02. Addresses with Towns
SELECT 
    e.`first_name`,
    e.`last_name`,
    t.`name`,
    a.`address_text`
FROM
    `employees` AS e
        JOIN
    `addresses` AS a ON e.`address_id` = a.`address_id`
        JOIN
    `towns` AS t ON a.`town_id` = t.`town_id`
ORDER BY e.`first_name` , e.`last_name`
LIMIT 5;

# 03. Sales Employee
SELECT 
    e.`employee_id`,
    e.`first_name`,
    e.`last_name`,
    d.`name`
FROM
    `employees` AS e
        JOIN
    `departments` AS d ON e.`department_id` = d.`department_id`
WHERE
    d.`name` = 'Sales'
ORDER BY e.`employee_id` DESC;

# 04. Employee Departments
SELECT 
    e.`employee_id`,
    e.`first_name`,
    e.`salary`, 
    d.`name`
FROM
    `employees` AS e
        JOIN
    `departments` AS d ON e.`department_id` = d.`department_id`
WHERE e.`salary` > 15000
ORDER BY d.`department_id` DESC
LIMIT 5;

# 05. Employees Without Project
SELECT 
    e.`employee_id`, e.`first_name`
FROM
    `employees` AS e
WHERE
    e.`employee_id` NOT IN (SELECT 
            `employee_id`
        FROM
            `employees_projects`)
ORDER BY e.`employee_id` DESC
LIMIT 3;

# 06. Employees Hired After
SELECT 
    e.`first_name`, e.`last_name`, e.`hire_date`, d.`name`
FROM
    `employees` AS e
        JOIN
    `departments` AS d ON e.`department_id` = d.`department_id`
WHERE
    DATE(e.`hire_date`) > '1991-01-01'
        AND d.`name` IN ('Sales' , 'Finance')
ORDER BY e.`hire_date` ASC;

# 07. Employees with Project
SELECT 
    e.`employee_id`, e.`first_name`, p.`name`
FROM
    `employees` AS e
        JOIN
    `employees_projects` AS ep ON e.`employee_id` = ep.`employee_id`
        JOIN
    `projects` AS p ON ep.`project_id` = p.`project_id`
WHERE
    DATE(p.`start_date`) > '2002-08-13'
        AND DATE(p.`end_date`) IS NULL
ORDER BY e.`first_name` , p.`name`
LIMIT 5;

# 08. Employee 24
SELECT 
    e.`employee_id`,
    e.`first_name`,
    IF(YEAR(p.`start_date`) >= 2005,
        NULL,
        p.`name`) AS `project_name`
FROM
    `employees` AS e
        LEFT JOIN
    `employees_projects` AS ep ON e.`employee_id` = ep.`employee_id`
        RIGHT JOIN
    `projects` AS p ON ep.`project_id` = p.`project_id`
WHERE
    e.`employee_id` = 24
ORDER BY p.`name`;

# 09. Employee Manager
SELECT 
    e.`employee_id`,
    e.`first_name`,
    m.`employee_id` AS 'manager_id',
    m.`first_name` AS 'manager_name'
FROM
    `employees` AS e
        JOIN
    `employees` AS m ON e.`manager_id` = m.`employee_id`
WHERE
    e.`manager_id` IN (3 , 7)
ORDER BY e.`first_name`;

# 10. Employee Summary
SELECT 
    e.`employee_id`,
    CONCAT_WS(' ', e.`first_name`, e.`last_name`) AS 'employee_name',
    CONCAT_WS(' ', m.`first_name`, m.`last_name`) AS 'manager_name',
    d.`name`
FROM
    `employees` AS e
        JOIN
    `employees` AS m ON e.`manager_id` = m.`employee_id`
        JOIN
    `departments` AS d ON e.`department_id` = d.`department_id`
ORDER BY e.`employee_id`
LIMIT 5;

# 11. Min Average Salary
SELECT 
    AVG(`salary`) AS 'min_avg_salary'
FROM
    `employees`
GROUP BY `department_id`
ORDER BY `min_avg_salary`
LIMIT 1;

USE `geography`;
# 12. Highest Peaks in Bulgaria
SELECT 
    mc.`country_code`,
    m.`mountain_range`,
    p.`peak_name`,
    p.`elevation`
FROM
    `mountains_countries` AS mc
        JOIN
    `mountains` AS m ON mc.`mountain_id` = m.`id`
        JOIN
    `peaks` AS p ON m.`id` = p.`mountain_id`
WHERE
    mc.`country_code` = 'BG' AND p.`elevation` > 2835
ORDER BY p.`elevation` DESC;

# 13. Count Mountain Ranges
SELECT 
    `country_code`, COUNT(*) AS 'mountain_range'
FROM
    `mountains_countries`
GROUP BY `country_code`
HAVING `country_code` IN ('US' , 'BG', 'RU')
ORDER BY `mountain_range` DESC;

# 14. Countries with Rivers
SELECT 
    c.`country_name`, r.`river_name`
FROM
    `countries` AS c
        LEFT JOIN
    `countries_rivers` AS cr ON c.`country_code` = cr.`country_code`
        LEFT JOIN
    `rivers` AS r ON r.`id` = cr.`river_id`
WHERE
    c.`continent_code` = 'AF'
ORDER BY `country_name` ASC
LIMIT 5;

# 16. Countries without any Mountains
SELECT 
    COUNT(*) AS 'country_count'
FROM
    `countries` AS c
WHERE
    c.`country_code` NOT IN (SELECT 
            `country_code`
        FROM
            `mountains_countries`);
            
# 17. Highest Peak and Longest River by Country
SELECT 
    c.`country_name`,
    MAX(p.`elevation`) AS 'highest_peak_elevation',
    MAX(r.`length`) AS 'longest_river_length'
FROM
    `countries` AS c
        JOIN
    `mountains_countries` AS mc ON c.`country_code` = mc.`country_code`
        JOIN
    `mountains` AS m ON mc.`mountain_id` = m.`id`
        JOIN
    `peaks` AS p ON p.`mountain_id` = m.`id`
        JOIN
    `countries_rivers` AS cr ON cr.`country_code` = c.`country_code`
        JOIN
    `rivers` AS r ON r.`id` = cr.`river_id`
GROUP BY c.`country_name`
ORDER BY `highest_peak_elevation` DESC , `longest_river_length` DESC , c.`country_name`
LIMIT 5;

# 15. *Continents and Currencies
SELECT *
FROM (SELECT  `continent_code`, `currency_code`, count(*) AS `count`
	FROM `countries`
	GROUP BY `continent_code`, `currency_code`
    HAVING `count` > 1
	ORDER BY `count` DESC) as `sorted`
GROUP BY sorted.`continent_code`
HAVING sorted.`currency_code` IS NOT NULL
ORDER BY sorted.`continent_code`, sorted.`currency_code`;

#15.2
SELECT  `continent_code`, `currency_code`, count(*) AS `count`
FROM `countries`
GROUP BY `continent_code`, `currency_code`
HAVING `count` > 1
ORDER BY `count` DESC;


# 15. *Continents and Currencies
SELECT continent_code, currency_code, COUNT(*) AS 'currency_usage'
FROM countries AS c
GROUP BY  continent_code, currency_code
HAVING currency_usage = (
	SELECT COUNT(*) AS 'coun'
    FROM countries AS c1
    WHERE c1.continent_code = c.continent_code
    GROUP BY currency_code
    ORDER BY coun DESC
    LIMIT 1
    ) AND currency_usage > 1
ORDER BY continent_code, currency_code;
