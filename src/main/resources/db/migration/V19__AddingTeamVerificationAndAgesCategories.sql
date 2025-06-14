ALTER TABLE dancing_teams
    ADD COLUMN is_verified boolean DEFAULT false;

CREATE TABLE team_age_category
(
    id      BIGSERIAL    NOT NULL PRIMARY KEY,
    team_id BIGINT       NOT NULL,
    name    varchar(120) NOT NULL,
    FOREIGN KEY (team_id) REFERENCES dancing_teams (id)
);