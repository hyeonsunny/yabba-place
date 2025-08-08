CREATE TABLE `game_review`
(
    `id`                  varchar(13) NOT NULL,
    `user_id`             bigint      NOT NULL,
    `game_date`           date        NOT NULL,
    `stadium_name`        varchar(50) NOT NULL,
    `home_team_name`      varchar(30) NOT NULL,
    `away_team_name`      varchar(30) NOT NULL,
    `home_score`          int DEFAULT 0,
    `away_score`          int DEFAULT 0,
    `seat_grade`          varchar(10) NOT NULL,
    `seat_section`        varchar(10) NOT NULL,
    `seat_number`         varchar(20) NOT NULL,
    `seat_view_image_url` text,
    `title`               varchar(30) NOT NULL,
    `content`             text,
    `rating`              int,
    `created_at`          datetime,
    `updated_at`          datetime,
    `deleted_at`          datetime,
    PRIMARY KEY (`id`)
);

CREATE TABLE `stadium`
(
    `id`         bigint      NOT NULL AUTO_INCREMENT,
    `team_id`    bigint      NOT NULL,
    `name`       varchar(12) NOT NULL,
    `address`    text,
    `created_at` datetime,
    `updated_at` datetime,
    `deleted_at` datetime,
    PRIMARY KEY (`id`)
);

CREATE TABLE `team`
(
    `id`         bigint      NOT NULL AUTO_INCREMENT,
    `stadium_id` bigint      NOT NULL,
    `name`       varchar(10) NOT NULL,
    `prefix`     varchar(10) NOT NULL,
    `created_at` datetime,
    `updated_at` datetime,
    `deleted_at` datetime,
    PRIMARY KEY (`id`)
);

CREATE TABLE `taste_review`
(
    `id`                 bigint      NOT NULL AUTO_INCREMENT,
    `user_id`            bigint      NOT NULL,
    `title`              varchar(30) NOT NULL,
    `content`            text NULL,
    `rating`             int NULL,
    `game_date`          date        NOT NULL,
    `stadium_name`       varchar(12) NOT NULL,
    `food_name`          varchar(30) NOT NULL,
    `food_location_type` varchar(20) NOT NULL,
    `food_price`         int NULL,
    `created_at`         datetime    NOT NULL,
    `updated_at`         datetime NULL,
    `deleted_at`         datetime NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `game_schedule`
(
    `id`              bigint      NOT NULL AUTO_INCREMENT,
    `home_team_id`    bigint      NOT NULL,
    `away_team_id`    bigint      NOT NULL,
    `status`          varchar(11) NOT NULL,
    `started_at`      datetime    NOT NULL,
    `home_team_score` int    DEFAULT 0,
    `away_team_score` int    DEFAULT 0,
    `winner_team_id`  bigint DEFAULT 0,
    `created_at`      datetime    NOT NULL,
    `updated_at`      datetime NULL,
    `deleted_at`      datetime NULL,
    PRIMARY KEY (`id`)
)
