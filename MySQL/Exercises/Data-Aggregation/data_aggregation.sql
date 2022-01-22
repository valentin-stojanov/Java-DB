USE `gringotts`;

# 1.Records' Count
SELECT COUNT(*) AS `count`
FROM `wizzard_deposits`;

# 2.Longest Magic Wand
-- SELECT `magic_wand_size` AS `longest_magic_wand`
SELECT max(`magic_wand_size`) AS `longest_magic_wand`
FROM `wizzard_deposits`;
-- ORDER BY `magic_wand_size` DESC
-- LIMIT 1;

# 3.Longest Magic Wand Per Deposit Groups
SELECT `deposit_group`, max(`magic_wand_size`) AS `longest_magic_wand`
FROM `wizzard_deposits`
GROUP BY `deposit_group`
ORDER BY `longest_magic_wand`, `deposit_group`;

# 04.Smallest Deposit Group per Magic Wand Size
SELECT `deposit_group`
FROM `wizzard_deposits`
GROUP BY `deposit_group`
ORDER BY avg(`magic_wand_size`)
LIMIT 1;

# 5.Deposits Sum
SELECT  `deposit_group`, sum(`deposit_amount`) AS `total_sum`
FROM `wizzard_deposits`
GROUP BY `deposit_group`
ORDER BY `total_sum` ASC;

# 6.Deposits Sum for Ollivander Family

# 7.Deposits Filter
SELECT  `deposit_group`, sum(`deposit_amount`) AS `total_sum`
FROM `wizzard_deposits`
WHERE `magic_wand_creator` = 'Ollivander family'
GROUP BY `deposit_group`
HAVING `total_sum` < 150000
ORDER BY `total_sum` DESC;

