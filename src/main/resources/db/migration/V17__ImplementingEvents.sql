CREATE TABLE addresses
(
    id          BIGSERIAL    NOT NULL PRIMARY KEY,
    street      varchar(150) NOT NULL,
    postal_code varchar(10)  NOT NULL,
    city        varchar(100) NOT NULL,
    country     varchar(100) NOT NULL,
    latitude    DECIMAL,
    longitude   DECIMAL
);


CREATE TABLE institutions
(
    id               BIGSERIAL    NOT NULL PRIMARY KEY,
    name             varchar(150) NOT NULL UNIQUE,
    description      TEXT,
    established_year integer      NOT NULL,
    website          varchar(300),
    files_uuid       uuid         NOT NULL,
    logo             varchar(255),
    created_at       timestamp,
    updated_at       timestamp,
    address_id       BIGINT       NOT NULL,
    contact_id       BIGINT       NOT NULL,
    user_id          BIGINT       NOT NULL,
    FOREIGN KEY (address_id) REFERENCES addresses (id),
    FOREIGN KEY (contact_id) REFERENCES contacts (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE events
(
    id           BIGSERIAL    NOT NULL PRIMARY KEY,
    title        varchar(150) NOT NULL,
    description  TEXT,
    start_date   timestamptz  NOT NULL,
    end_date     timestamptz  NOT NULL,
    address_id   BIGINT       NOT NULL,
    ticket_price DECIMAL,
    type   varchar(150) NOT NULL,
    files_uuid   uuid         NOT NULL,
    poster       varchar(200),
    created_at   timestamp    NOT NULL,
    updated_at   timestamp    NOT NULL,
    FOREIGN KEY (address_id) REFERENCES addresses (id)
);

CREATE TABLE event_team_institution
(
    id              BIGSERIAL NOT NULL PRIMARY KEY,
    event_id        BIGINT    NOT NULL,
    team_id         BIGINT,       -- Connecting with dancing team
    institution_id  BIGINT,       -- Connecting with institution
    connection_type varchar(100), -- Enum: Created by taking part

    FOREIGN KEY (event_id) REFERENCES events (id),
    FOREIGN KEY (team_id) REFERENCES dancing_teams (id),
    FOREIGN KEY (institution_id) REFERENCES institutions (id)

);