TRUNCATE lobbies CASCADE;

ALTER TABLE lobbies
    ADD created_by_player_id VARCHAR(255);

ALTER TABLE lobbies
    ALTER COLUMN created_by_player_id SET NOT NULL;