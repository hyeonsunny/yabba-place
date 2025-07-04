CREATE TABLE `game_review`
(
    `id`                  bigint      NOT NULL AUTO_INCREMENT,
    `user_id`             bigint      NOT NULL,
    `game_date`           date        NOT NULL,
    `stadium_name`        varchar(50) NOT NULL,
    `home_team_name`      varchar(30) NOT NULL,
    `away_team_name`      varchar(30) NOT NULL,
    `home_score`          int DEFAULT 0,
    `away_score`          int DEFAULT 0,
    `seat_grade_name`     varchar(10) NOT NULL,
    `seat_section`        varchar(10) NOT NULL,
    `seat_number`         varchar(20) NOT NULL,
    `seat_view_image_url` text,
    `title`               varchar(30) NOT NULL,
    `content`             text,
    `rating`              int,
    `created_at`          datetime,
    `updated_at`          datetime,
    PRIMARY KEY (`id`)
);
