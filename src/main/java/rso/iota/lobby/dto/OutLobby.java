package rso.iota.lobby.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
public class OutLobby {
    @NotNull
    UUID id;

    @NotNull
    String name;

    @NotNull
    String description;

    @NotNull
    Integer maxPlayers;


    @NotNull
    Integer currentPlayers;

    @NotNull
    String owner;

    @NotNull
    OffsetDateTime createdAt;
}
