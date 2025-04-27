CREATE TABLE contacts
(
    id           BIGSERIAL NOT NULL PRIMARY KEY,
    phone_number varchar(20),
    email        varchar(150)
);

ALTER TABLE dancing_teams ADD contact_id BIGINT;
ALTER TABLE dancing_teams ADD CONSTRAINT fk_contact FOREIGN KEY (contact_id) REFERENCES contacts(id);

