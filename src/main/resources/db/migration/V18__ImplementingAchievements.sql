CREATE TABLE people (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    first_name varchar(120) NOT NULL,
    last_name varchar(120) NOT NULL,
    dancing_team_id BIGINT NOT NULL,
    FOREIGN KEY (dancing_team_id) REFERENCES dancing_teams(id)
);

CREATE TABLE people_positions (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    person_id BIGINT NOT NULL,
    position varchar(60) NOT NULL,
    since date,
    FOREIGN KEY (person_id) REFERENCES people(id)
);

CREATE TABLE achievements (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    name varchar(300) NOT NULL,
    date timestamptz,
    year integer,
    event_id BIGINT,
    city varchar(120) NOT NULL,
    description TEXT,
    organizer varchar(250) NOT NULL,
    category varchar(120) NOT NULL,
    level varchar(120) NOT NULL,
    dancing_team_id BIGINT NOT NULL,
    FOREIGN KEY (event_id) REFERENCES events(id),
    FOREIGN KEY (dancing_team_id) REFERENCES dancing_teams(id)
);

CREATE TABLE achievements_distinguished_people (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    achievement_id BIGSERIAL NOT NULL,
    person_id BIGINT NOT NULL,
    FOREIGN KEY (achievement_id) REFERENCES achievements(id),
    FOREIGN KEY (person_id) REFERENCES people(id)
);