TRUNCATE lobbies;
DELETE
FROM lobbies;

CREATE TABLE live_data
(
    id              UUID                        NOT NULL,
    created_at      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    player_username VARCHAR(255)                NOT NULL,
    player_size     INTEGER                     NOT NULL,
    lobby_id        UUID                        NOT NULL,
    CONSTRAINT pk_live_data PRIMARY KEY (id)
);

ALTER TABLE lobbies
    ADD archived BOOLEAN;

ALTER TABLE lobbies
    ADD game_id VARCHAR(255);

ALTER TABLE lobbies
    ADD server_id VARCHAR(255);

ALTER TABLE lobbies
    ALTER COLUMN archived SET NOT NULL;

ALTER TABLE lobbies
    ALTER COLUMN game_id SET NOT NULL;

ALTER TABLE lobbies
    ALTER COLUMN server_id SET NOT NULL;

ALTER TABLE lobbies
    ADD CONSTRAINT uc_lobbies_gameid UNIQUE (game_id);

ALTER TABLE live_data
    ADD CONSTRAINT FK_LIVE_DATA_ON_LOBBY FOREIGN KEY (lobby_id) REFERENCES lobbies (id);

CREATE INDEX idx_livedata_lobby_id ON live_data (lobby_id);

ALTER TABLE lobbies
    DROP COLUMN description;