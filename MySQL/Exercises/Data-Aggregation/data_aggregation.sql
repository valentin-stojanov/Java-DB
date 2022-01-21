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