TRUNCATE lobbies CASCADE;

ALTER TABLE lobbies
    ADD current_players INTEGER;

ALTER TABLE lobbies
    ALTER COLUMN current_players SET NOT NULL;

ALTER TABLE live_data
    DROP COLUMN player_size;

ALTER TABLE live_data
    ADD player_size FLOAT NOT NULL;