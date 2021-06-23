--liquibase formatted sql

--changeset alis:init-data/1 context:local
INSERT INTO app_user(id, username, first_name, password, LANGUAGE, user_active, role)
VALUES (1, 'admin', 'Admin', '$2a$10$X0Dn6THuWo2t49OQUhIsOe.UCB.lc63wGvTkl4GUxGOaH5Tg0bu7.', 'en', TRUE, 0);
