# 1.Find Book Titles
SELECT `title`
FROM `books`
-- WHERE substring(`title`, 1, 3) = 'The' 
WHERE `title` LIKE 'The%'
ORDER BY `id`;

# 2.Replace Titles
SELECT replace(`title`, 'The', '***') as 'Title'
FROM `books`
WHERE substring(`title`, 1, 3) = 'The'
ORDER BY `id`;

# 3.Sum Cost of All Books
SELECT round(sum(`cost`), 2)
FROM `books`;

# 4.Days Lived
SELECT concat_ws(' ', `first_name`, `last_name`) AS `Full Name`, timestampdiff(DAY, `born`, `died`) AS 'Days Lived'
FROM `authors`;

# 5.Harry Potter Books
SELECT `title`
FROM `books`
WHERE `title` LIKE 'Harry Potter%'
ORDER BY `id`;