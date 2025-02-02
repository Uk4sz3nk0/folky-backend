CREATE TABLE social_media
(
    id              BIGSERIAL NOT NULL PRIMARY KEY,
    dancing_team_id BIGINT    NOT NULL,
    facebook_url    text,
    instagram_url   text,
    youtube_url     text,
    twitter_url     text,
    tiktok_url      text,
    FOREIGN KEY (dancing_team_id) REFERENCES dancing_teams (id)
);

ALTER TABLE dancing_teams
    ADD COLUMN social_media_id BIGINT,
    ADD CONSTRAINT social_media_fk FOREIGN KEY (social_media_id) REFERENCES social_media (id);
