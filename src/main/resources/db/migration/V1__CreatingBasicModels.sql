CREATE TABLE roles
(
    id   BIGSERIAL   NOT NULL PRIMARY KEY,
    name varchar(30) NOT NULL
);

CREATE TABLE users
(
    id                           BIGSERIAL   NOT NULL PRIMARY KEY,
    first_name                   varchar(45) NOT NULL,
    last_name                    varchar(45) NOT NULL,
    email                        varchar(75) NOT NULL,
    password                     varchar(24) NOT NULL,
    brith_date                   timestamptz NOT NULL,
    how_long_dancing             integer     NOT NULL DEFAULT 0,
    how_long_playing_instruments integer     NOT NULL DEFAULT 0
);

INSERT INTO roles (name)
VALUES ('admin');
INSERT INTO roles (name)
VALUES ('dancer');
INSERT INTO roles (name)
VALUES ('user');
INSERT INTO roles (name)
VALUES ('guest');
INSERT INTO roles (name)
VALUES ('director');
