USE notes;

DROP TABLE IF EXISTS `notes`;

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users`
(
    `id`               bigint       NOT NULL AUTO_INCREMENT,
    `create_time`      timestamp    NULL DEFAULT NULL,
    `email`            varchar(255) NOT NULL,
    `last_update_time` timestamp    NULL DEFAULT NULL,
    `password`         varchar(255)      DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

INSERT INTO `users` (`id`, `create_time`, `email`, `last_update_time`, `password`)
VALUES (1, '2021-01-17 10:08:27', 'test.one@gmail.com',  '2021-01-17 10:08:32', '$2y$13$n0ynBaZ.GadLpt8SVLvJAu2Xk8/K5.pQ5Qn0I40g6YkTMP13EEB6a'),
       (2, '2021-01-17 10:08:27', 'test.two@gmail.com',  '2021-01-17 10:08:32', '$2y$13$n0ynBaZ.GadLpt8SVLvJAu2Xk8/K5.pQ5Qn0I40g6YkTMP13EEB6a'),
       (3, '2021-01-17 10:08:27', 'test.tree@gmail.com', '2021-01-17 10:08:32', '$2y$13$n0ynBaZ.GadLpt8SVLvJAu2Xk8/K5.pQ5Qn0I40g6YkTMP13EEB6a'),
       (4, '2021-01-17 10:08:27', 'test.four@gmail.com', '2021-01-17 10:08:32', '$2y$13$n0ynBaZ.GadLpt8SVLvJAu2Xk8/K5.pQ5Qn0I40g6YkTMP13EEB6a'),
       (5, '2021-01-17 10:08:27', 'test.five@gmail.com', '2021-01-17 10:08:32', '$2y$13$n0ynBaZ.GadLpt8SVLvJAu2Xk8/K5.pQ5Qn0I40g6YkTMP13EEB6a');

CREATE TABLE `notes`
(
    `id`               bigint      NOT NULL AUTO_INCREMENT,
    `create_time`      timestamp   NULL DEFAULT NULL,
    `last_update_time` timestamp   NULL DEFAULT NULL,
    `note`             varchar(1000)    DEFAULT NULL,
    `title`            varchar(50) NOT NULL,
    `user_id`          bigint           DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKsncua5ftnqcfg0q2pg8ylcs3x` (`user_id`),
    CONSTRAINT `FKsncua5ftnqcfg0q2pg8ylcs3x` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

INSERT INTO `notes` (`id`, `create_time`, `last_update_time`, `note`, `title`, `user_id`)
VALUES (1, '2015-10-22 23:34:40', '2020-03-18 01:18:32', 'Note One Text',  'Note One Title',  1),
       (2, '2015-10-22 23:34:40', '2020-03-18 01:18:32', 'Note Two Text',  'Note Two Title',  2),
       (3, '2015-10-22 23:34:40', '2020-03-18 01:18:32', 'Note Tree Text', 'Note Tree Title', 3),
       (4, '2015-10-22 23:34:40', '2020-03-18 01:18:32', 'Note Four Text', 'Note Four Title', 4),
       (5, '2015-10-22 23:34:40', '2020-03-18 01:18:32', 'Note Five Text', 'Note Five Title', 5);