package rso.iota.lobby.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class OutLobby {
    @NotNull
    UUID id;

    @NotNull
    String name;

    @NotNull
    Integer maxPlayers;

    @NotNull
    Integer currentPlayers;

    @NotNull
    Boolean archived;

    @NotNull
    String owner;

    @NotNull
    OffsetDateTime createdAt;

    @NotNull
    String serverId;

    @NotNull
    String gameId;

    @NotNull
    List<OutLiveData> liveData;
}