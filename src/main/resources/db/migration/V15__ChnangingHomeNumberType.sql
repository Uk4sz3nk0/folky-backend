ALTER TABLE dancing_teams
ALTER COLUMN home_number TYPE varchar(120)
USING home_number::VARCHAR;