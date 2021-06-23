--liquibase formatted sql

--changeset alis:init/1
CREATE SEQUENCE hibernate_sequence START 100 INCREMENT 1;

CREATE TABLE app_user
(
    id          INT NOT NULL,
    email       VARCHAR(255),
    username    VARCHAR(255),
    first_name  VARCHAR(255),
    last_name   VARCHAR(255),
    PASSWORD    VARCHAR(255),
    reset_token VARCHAR(255),
    LANGUAGE    VARCHAR(255),
    birth_place VARCHAR(255),
    birth_date  TIMESTAMP,
    ROLE        VARCHAR(255),
    user_active BOOLEAN,
    created_at  TIMESTAMP,
    PRIMARY KEY (id)
);

ALTER TABLE app_user
    ADD CONSTRAINT uk_email_in_table_app_user UNIQUE (email);

ALTER TABLE app_user
    ADD CONSTRAINT uk_username_in_table_app_user UNIQUE (username);

