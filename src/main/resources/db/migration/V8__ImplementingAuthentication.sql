CREATE TABLE user_role
(
    id              BIGSERIAL NOT NULL PRIMARY KEY,
    user_id         BIGINT    NOT NULL,
    role_id         BIGINT    NOT NULL,
    dancing_team_id BIGINT    NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id),
    FOREIGN KEY (dancing_team_id) REFERENCES dancing_teams (id)
);

ALTER TABLE users
    ADD COLUMN uid varchar(256) NOT NULL default '';