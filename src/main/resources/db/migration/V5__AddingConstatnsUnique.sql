ALTER TABLE dances ADD CONSTRAINT unique_name UNIQUE (name);
ALTER TABLE dancing_teams ADD CONSTRAINT unique_dancing_team_name UNIQUE (name);
ALTER TABLE music_instruments ADD CONSTRAINT unique_instrument_name UNIQUE (name);
ALTER TABLE music_instruments_types ADD CONSTRAINT unique_instrument_type_name UNIQUE (name);
ALTER TABLE regions ADD CONSTRAINT unique_region_name UNIQUE (name);
ALTER TABLE roles ADD CONSTRAINT unique_role_name UNIQUE (name);
ALTER TABLE users ADD CONSTRAINT unique_user_email UNIQUE (email);