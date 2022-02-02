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