CREATE DATABASE `exam`;
USE `exam`;

CREATE TABLE `towns`(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE `addresses`(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(50) NOT NULL UNIQUE,
`town_id` INT NOT NULL,
CONSTRAINT fk_addresses_towns
FOREIGN KEY (town_id)
REFERENCES towns(id)
);

CREATE TABLE `stores`(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(20) NOT NULL UNIQUE,
`rating` FLOAT NOT NULL,
`has_parking` BOOLEAN DEFAULT FALSE,
`address_id` INT NOT NULL,
CONSTRAINT fk_stores_addresses
FOREIGN KEY (address_id)
REFERENCES addresses(id)
);

CREATE TABLE `employees`(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`first_name` VARCHAR(15) NOT NULL,
`middle_name` CHAR(1),
`last_name` VARCHAR(20) NOT NULL,
`salary` DECIMAL(19,2) DEFAULT 0 NOT NULL,
`hire_date` DATE NOT NULL,
`manager_id` INT,
`store_id` INT NOT NULL,
CONSTRAINT fk_employees_stores
FOREIGN KEY (store_id)
REFERENCES stores(id)
);

ALTER TABLE employees
ADD CONSTRAINT fk_employees
FOREIGN KEY (manager_id)
REFERENCES employees(id);

CREATE TABLE `pictures`(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`url` VARCHAR(100) NOT NULL,
`added_on` DATETIME NOT NULL
);

CREATE TABLE `categories`(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(40) NOT NULL UNIQUE
);

CREATE TABLE `products`(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(40) NOT NULL UNIQUE,
`best_before` DATE NOT NULL,
`price` DECIMAL(10,2) NOT NULL,
`description` TEXT,
`category_id` INT NOT NULL,
`picture_id` INT NOT NULL,
CONSTRAINT fk_products_categories
FOREIGN KEY (category_id)
REFERENCES categories(id),
CONSTRAINT fk_products_pictures
FOREIGN KEY (picture_id)
REFERENCES pictures(id)
);

CREATE TABLE `products_stores`(
`product_id` INT,
`store_id` INT,
CONSTRAINT fk_products_stores_products
FOREIGN KEY (product_id)
REFERENCES products(id),
CONSTRAINT fk_products_stores_stores
FOREIGN KEY (store_id)
REFERENCES stores(id)
);

ALTER TABLE products_stores
ADD CONSTRAINT pk_products_stores
PRIMARY KEY (product_id, store_id );

# 2. Insert
INSERT INTO `products_stores` (product_id, store_id)
SELECT p.id, 1
FROM `products` AS p
WHERE p.id NOT IN(SELECT product_id FROM products_stores);

# 03. Update
UPDATE employees
SET manager_id = 3, salary = salary - 500
WHERE YEAR(hire_date) > 2003 AND store_id NOT IN(5, 14);

# 04. Delete
DELETE FROM employees
WHERE manager_id IS NOT NULL AND salary > 6000;

#5. Employees 
SELECT first_name, middle_name, last_name, salary, hire_date
FROM employees
ORDER BY hire_date DESC;

# 6.Products with old pictures
SELECT pr.`name`, pr.price, pr.best_before, CONCAT(LEFT(pr.`description`, 10), '...') AS short_description, p.url
FROM products AS pr
JOIN pictures AS p
ON pr.picture_id = p.id
WHERE char_length(pr.`description`) > 100 AND
	YEAR(p.added_on) < 2019 AND
    pr.price > 20
ORDER BY pr.price DESC;

# 07. Counts of products in stores
SELECT st.`name`, count(pr.id) AS product_count, round(avg(pr.price), 2) AS 'avg'
FROM stores AS st
LEFT JOIN products_stores AS ps
ON st.id = ps.store_id
LEFT JOIN products AS pr
ON pr.id = ps.product_id
GROUP BY st.`name`
ORDER BY product_count DESC, `avg` DESC, st.id;

# 08. Specific employee
SELECT concat_ws(' ', e.first_name, e.last_name) AS 'Full_name', st.`name`, a.`name`, e.salary
FROM employees AS e
JOIN stores AS st
ON e.store_id = st.id
JOIN addresses AS a
ON st.address_id = a.id
WHERE e.salary < 4000 AND a.`name` LIKE '%5%' AND char_length(st.`name`) > 8 AND e.last_name LIKE '%n'
LIMIT 1;

# 09. Find all information of stores
SELECT reverse(st.`name`) AS reversed_name, concat_ws('-', upper(tw.`name`), a.`name`) AS full_address, count(e.id) AS employees_count
FROM employees AS e
JOIN stores AS st
ON e.store_id = st.id
JOIN addresses AS a
ON st.address_id = a.id
JOIN towns AS tw
ON a.town_id = tw.id
GROUP BY st.`name`
ORDER BY full_address ASC;

#10. Find full name of top paid employee by store name
DELIMITER $$
CREATE FUNCTION udf_top_paid_employee_by_store(store_name VARCHAR(50))
RETURNS TEXT
DETERMINISTIC
BEGIN
	RETURN (SELECT concat(e.first_name, ' ', e.middle_name, '. ', e.last_name,  ' works in store for ',  (YEAR('2020-10-18')- YEAR(hire_date )), ' years' )
			FROM employees AS e
			JOIN stores AS st
			ON e.store_id = st.id
			WHERE st.`name` = store_name
			ORDER BY e.salary DESC
			LIMIT 1);
END$$
DELIMITER ;

SELECT udf_top_paid_employee_by_store('Stronghold') as 'full_info';

# 11. Update product price by address
DELIMITER $$
CREATE PROCEDURE udp_update_product_price (address_name VARCHAR (50))
BEGIN
	DECLARE product_id INT;
	DECLARE coefficient INT;	
    
    SET product_id := (SELECT pr.id
			FROM products AS pr
			JOIN products_stores AS ps
			ON pr.id = ps.product_id
			JOIN stores AS st
			ON ps.store_id = st.id
			JOIN addresses as ad
			ON st.address_id = ad.id
			WHERE ad.`name` = address_name
            ORDER BY pr.id DESC
            LIMIT 1);
	SET coefficient := IF(address_name LIKE '0%', 100, 200);
    
	UPDATE products
    SET price = price + coefficient
    WHERE id = product_id;
END$$
DELIMITER ;

CALL udp_update_product_price('07 Armistice Parkway');
SELECT name, price FROM products WHERE id = 15;

CALL udp_update_product_price('1 Cody Pass');
SELECT name, price FROM products WHERE id = 17;

SELECT pr.id
			FROM products AS pr
			JOIN products_stores AS ps
			ON pr.id = ps.product_id
			JOIN stores AS st
			ON ps.store_id = st.id
			JOIN addresses as ad
			ON st.address_id = ad.id
			WHERE ad.`name` = '1 Cody Pass';