CREATE TABLE lobbies
(
    id          UUID                        NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    name        VARCHAR(100)                NOT NULL,
    description VARCHAR(200)                NOT NULL,
    max_players INTEGER                     NOT NULL,
    CONSTRAINT pk_lobbies PRIMARY KEY (id)
);

ALTER TABLE lobbies
    ADD CONSTRAINT uc_lobbies_name UNIQUE (name);