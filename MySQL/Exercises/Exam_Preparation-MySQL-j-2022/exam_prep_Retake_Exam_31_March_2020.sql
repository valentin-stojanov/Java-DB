CREATE DATABASE `insta_influencers`;
USE `insta_influencers`;

CREATE TABLE `users`(
`id` INT PRIMARY KEY, -- AUTO_INCREMENT,
`username` VARCHAR(30) NOT NULL UNIQUE,
`password` VARCHAR(30) NOT NULL,
`email` VARCHAR(50) NOT NULL,
`gender` CHAR(1) NOT NULL,
`age` INT NOT NULL,
`job_title` VARCHAR(40) NOT NULL,
`ip` VARCHAR(30) NOT NULL
);

CREATE TABLE `addresses`(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`address` VARCHAR(30) NOT NULL,
`town` VARCHAR(30) NOT NULL,
`country` VARCHAR(30) NOT NULL,
`user_id` INT NOT NULL,
CONSTRAINT fk_addresses_users
FOREIGN KEY (`user_id`)
REFERENCES `users`(`id`)
);

CREATE TABLE `photos`(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`description` TEXT NOT NULL,
`date` DATETIME NOT NULL,
`views` INT NOT NULL DEFAULT 0
);


CREATE TABLE `comments`(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`comment` VARCHAR(255) NOT NULL,
`date` DATETIME NOT NULL,
`photo_id` INT NOT NULL,
CONSTRAINT fk_comments_photos
FOREIGN KEY (`photo_id`)
REFERENCES `photos`(`id`)
);

CREATE TABLE `users_photos`(
`user_id` INT NOT NULL,
`photo_id` INT NOT NULL,
CONSTRAINT fk_users_photos_users
FOREIGN KEY (`user_id`)
REFERENCES `users`(`id`),
CONSTRAINT fk_users_photos_photos
FOREIGN KEY (`photo_id`)
REFERENCES `photos`(`id`)
);

CREATE TABLE `likes`(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`photo_id` INT,
`user_id` INT,
CONSTRAINT fk_likes_photos
FOREIGN KEY (`photo_id`)
REFERENCES `photos`(`id`),
CONSTRAINT fk_likes_users
FOREIGN KEY (`user_id`)
REFERENCES `users`(`id`)
);

# 02. Insert

INSERT INTO `addresses` (`address`, `town`, `country`,`user_id`)
SELECT `username`, `password`, `ip`, `age`
FROM `users`
WHERE gender = 'M';



# 03. Update
-- Solution 1
UPDATE `addresses`
SET `country` = 'Blocked'
WHERE `country` LIKE 'B%';

UPDATE `addresses`
SET `country` = 'Test'
WHERE `country` LIKE 'T%';

UPDATE `addresses`
SET `country` = 'In Progress'
WHERE `country` LIKE 'P%';

-- Solution 2
UPDATE `addresses`
SET `country` = IF(`country` LIKE 'B%', 'Blocked', IF(`country` LIKE 'T%', 'Test', IF(`country` LIKE 'P%', 'In Progress', `country`)));

# 04. Delete
DELETE FROM `addresses`
WHERE `id` % 3 = 0;

# 05. Users
SELECT `username`, `gender`, `age`
FROM `users`
ORDER BY `age` DESC, `username`;

# 06. Extract 5 Most Commented Photos

SELECT p.`id`, p.`date`, p.`description`, count(*) AS 'commentsCounts'
FROM `photos` AS p
JOIN `comments` AS c
ON c.`photo_id` = p.`id`
GROUP BY `photo_id`
ORDER BY `commentsCounts` DESC, p.`id`
LIMIT 5;

# 07. Lucky Users
SELECT 
    CONCAT(u.`id`, ' ', u.`username`) AS 'id_username',
    u.`email`
FROM
    `users` AS u
        JOIN
    `users_photos` AS up ON u.`id` = up.`user_id`
WHERE
    `user_id` = `photo_id`
ORDER BY u.`id`;