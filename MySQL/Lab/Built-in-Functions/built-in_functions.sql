# 1.Find Book Titles
SELECT `title`
FROM `books`
-- WHERE substring(`title`, 1, 3) = 'The' 
WHERE `title` LIKE 'The%'
ORDER BY `id`;