CREATE TABLE dancing_teams
(
    id            BIGSERIAL   NOT NULL PRIMARY KEY,
    name          varchar(45) NOT NULL,
    files_uuid    varchar(60) NOT NULL,
    description   text,
    creation_date date,
    director_id   BIGINT      NOT NULL,
    region_id     BIGINT      NOT NULL,
    city          varchar(40) DEFAULT '',
    street        varchar(40) DEFAULT '',
    home_number   integer     default -1,
    flat_number   integer     default -1,
    zip_code      varchar(8)  default '',
    FOREIGN KEY (director_id) REFERENCES users (id),
    FOREIGN KEY (region_id) REFERENCES regions (id)
);

CREATE TABLE dancing_team_dances
(
    id       BIGSERIAL NOT NULL PRIMARY KEY,
    team_id  BIGINT    NOT NULL,
    dance_id BIGINT    NOT NULL,
    FOREIGN KEY (team_id) REFERENCES dancing_teams (id),
    FOREIGN KEY (dance_id) REFERENCES dances (id)
);

CREATE TABLE dancing_team_dancers
(
    id        BIGSERIAL NOT NULL PRIMARY KEY,
    team_id   BIGINT    NOT NULL,
    dancer_id BIGINT    NOT NULL,
    FOREIGN KEY (team_id) REFERENCES dancing_teams (id),
    FOREIGN KEY (dancer_id) REFERENCES users (id)
);

CREATE TABLE dancing_team_musicians
(
    id          BIGSERIAL NOT NULL PRIMARY KEY,
    team_id     BIGINT    NOT NULL,
    musician_id BIGINT    NOT NULL,
    FOREIGN KEY (team_id) REFERENCES dancing_teams (id),
    FOREIGN KEY (musician_id) REFERENCES users (id)
);