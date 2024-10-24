ALTER TABLE users
    ADD COLUMN preferred_language varchar(8);
ALTER TABLE users
    ADD COLUMN want_receive_push_notifications boolean DEFAULT false;
ALTER TABLE users
    ADD COLUMN want_receive_email_notifications boolean DEFAULT false;

-- User devices
CREATE TABLE device_tokens
(
    id          BIGSERIAL    NOT NULL PRIMARY KEY,
    user_id     BIGINT       NOT NULL,
    token       varchar(500) NOT NULL,
    device_type varchar(60)  NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE subscriptions
(
    id              BIGSERIAL NOT NULL PRIMARY KEY,
    user_id         BIGINT    NOT NULL,
    dancing_team_id BIGINT    NOT NULL,
    FOREIGN KEY (user_id) references users (id),
    FOREIGN KEY (dancing_team_id) references dancing_teams (id)
);

CREATE TABLE translations_dictionary
(
    id        BIGSERIAL    NOT NULL PRIMARY KEY,
    language  varchar(8)   NOT NULL,
    specifier varchar(100) NOT NULL,
    value     varchar(100) NOT NULL
);