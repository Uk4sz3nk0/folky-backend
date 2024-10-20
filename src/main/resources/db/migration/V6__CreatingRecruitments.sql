CREATE TABLE recruitments
(
    id                       BIGSERIAL NOT NULL PRIMARY KEY,
    start_date               date      NOT NULL,
    end_date                 date      NOT NULL,
    description              text,
    requirements             varchar(256)[],
    is_opened                boolean,
    is_ended_by_time         boolean,
    is_ended_before_end_date boolean,
    dancing_team_id          BIGINT    NOT NULL,
    FOREIGN KEY (dancing_team_id) REFERENCES dancing_teams (id)
);

CREATE TABLE recruitment_requests
(
    id             BIGSERIAL   NOT NULL PRIMARY KEY,
    user_id        BIGINT      NOT NULL,
    recruitment_id BIGINT      NOT NULL,
    state          varchar(60) NOT NULL, -- State of request like: SENT, READ, ACCEPTED, REJECTED
    type           varchar(60) NOT NULL, -- Type of request: User -> Team, Team -> User
    message        text,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (recruitment_id) REFERENCES recruitments (id)
);


