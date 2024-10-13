ALTER TABLE dancing_teams
    ADD COLUMN directory_uuid uuid;
ALTER TABLE dancing_teams
    ADD COLUMN logo_filename varchar(100);
ALTER TABLE dancing_teams
    ADD COLUMN banner_filename varchar(100);
ALTER TABLE dancing_teams
    ADD COLUMN is_recruitment_opened boolean default false;
