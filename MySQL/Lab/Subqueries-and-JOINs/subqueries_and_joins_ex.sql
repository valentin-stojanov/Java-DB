
# 1. Managers
SELECT employee_id, concat(`first_name`, ' ', `last_name`) AS ' full_name', d.department_id, d.name
FROM employees AS e
JOIN departments AS d
ON e.employee_id = d.manager_id
ORDER BY employee_id
LIMIT 5;

# 2. Towns Addresses
SELECT t.`town_id`, t.`name`, a.`address_text`
FROM `addresses` AS a
JOIN `towns` AS t
ON t.`town_id` = a.`town_id`
WHERE t.`name` IN('San Francisco', 'Sofia', 'Carnation')
ORDER BY t.`town_id`, a.`address_id`;