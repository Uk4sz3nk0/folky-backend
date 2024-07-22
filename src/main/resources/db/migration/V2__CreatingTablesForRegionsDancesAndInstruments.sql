CREATE TABLE regions
(
    id     BIGSERIAL   NOT NULL PRIMARY KEY,
    locale varchar(10) NOT NULL, -- Describes country, for example pl_PL for polish regions, de_DE for german regions
    name   varchar(50) NOT NULL
);

INSERT INTO regions(locale, name)
VALUES ('pl_PL', 'LOWER_SILESIAN');
INSERT INTO regions(locale, name)
VALUES ('pl_PL', 'KUYAVIAN_POMERANIAN');
INSERT INTO regions(locale, name)
VALUES ('pl_PL', 'LUBLIN');
INSERT INTO regions(locale, name)
VALUES ('pl_PL', 'LUBUSZ');
INSERT INTO regions(locale, name)
VALUES ('pl_PL', 'LODZ');
INSERT INTO regions(locale, name)
VALUES ('pl_PL', 'LESSER_POLAND');
INSERT INTO regions(locale, name)
VALUES ('pl_PL', 'MASOVIAN');
INSERT INTO regions(locale, name)
VALUES ('pl_PL', 'OPOLE');
INSERT INTO regions(locale, name)
VALUES ('pl_PL', 'SUBCARPATHIAN');
INSERT INTO regions(locale, name)
VALUES ('pl_PL', 'PODLASKIE');
INSERT INTO regions(locale, name)
VALUES ('pl_PL', 'POMERANIAN');
INSERT INTO regions(locale, name)
VALUES ('pl_PL', 'SILESIAN');
INSERT INTO regions(locale, name)
VALUES ('pl_PL', 'SWIETOKRZYSKIE');
INSERT INTO regions(locale, name)
VALUES ('pl_PL', 'WARMIAN_MASURIAN');
INSERT INTO regions(locale, name)
VALUES ('pl_PL', 'GREATER_POLAND');
INSERT INTO regions(locale, name)
VALUES ('pl_PL', 'WEST_POMERANIAN');

CREATE TABLE dances
(
    id     BIGSERIAL   NOT NULL PRIMARY KEY,
    locale varchar(10) NOT NULL, -- Describes country, for example pl_PL for polish regions, de_DE for german regions
    name   varchar(50) NOT NULL
);

INSERT INTO dances (locale, name)
VALUES ('pl_PL', 'OBEREK');
INSERT INTO dances (locale, name)
VALUES ('pl_PL', 'POLKA');

CREATE TABLE region_dance
(
    id        BIGSERIAL NOT NULL PRIMARY KEY,
    region_id BIGINT    NOT NULL,
    dance_id  BIGINT    NOT NULL,
    FOREIGN KEY (region_id) REFERENCES regions (id),
    FOREIGN KEY (dance_id) REFERENCES dances (id)
);

CREATE TABLE music_instruments_types
(
    id   BIGSERIAL   NOT NULL PRIMARY KEY,
    name varchar(40) NOT NULL
);

INSERT INTO music_instruments_types (name)
VALUES ('STRING');
INSERT INTO music_instruments_types (name)
VALUES ('WIND');
INSERT INTO music_instruments_types (name)
VALUES ('WOODWIND');
INSERT INTO music_instruments_types (name)
VALUES ('BRASS');
INSERT INTO music_instruments_types (name)
VALUES ('PERCUSSION');
INSERT INTO music_instruments_types (name)
VALUES ('KEYBOARD');
INSERT INTO music_instruments_types (name)
VALUES ('PLUCKED_STRING');

CREATE TABLE music_instruments
(
    id      BIGSERIAL   NOT NULL PRIMARY KEY,
    name    varchar(50) NOT NULL,
    type_id BIGINT      NOT NULL,
    FOREIGN KEY (type_id) REFERENCES music_instruments_types (id)
);

CREATE TABLE user_instruments
( -- Who is playing on what
    id                  BIGSERIAL NOT NULL PRIMARY KEY,
    user_id             BIGINT    NOT NULL,
    music_instrument_id BIGINT    NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE user_dances
( -- Who is dancing what
    id       BIGSERIAL NOT NULL PRIMARY KEY,
    user_id  BIGINT    NOT NULL,
    dance_id BIGINT    NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (dance_id) REFERENCES dances (id)
);