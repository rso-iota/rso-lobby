package rso.iota.lobby.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;
import rso.iota.lobby.dto.CreateLobby;
import rso.iota.lobby.dto.OutLiveData;
import rso.iota.lobby.dto.OutLobby;
import rso.iota.lobby.dto.error.NotFoundException;
import rso.iota.lobby.model.LiveData;
import rso.iota.lobby.model.Lobby;
import rso.iota.lobby.repository.LobbyRepository;
import rso.iota.lobby.service.domain.NewGameResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class LobbyService {
    private final LobbyRepository lobbyRepository;
    private final GameAdminAppService gameAdminAppService;


    public void deleteLobby(UUID id, String userId) {
        Lobby lobby = lobbyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Lobby with serverId %s not found", id)));

        if (!lobby.getCreatedByPlayerId().equals(userId)) {
            throw new ServiceException("You are not the owner of this lobby");
        }

        try {
            gameAdminAppService.deleteGame(lobby.getServerId(), lobby.getGameId());
        } catch (Exception e) {
            log.warn("Failed to delete game with serverId {} and gameId {}. Proceeding with lobby archive",
                    lobby.getServerId(),
                    lobby.getGameId());
        }

        if (lobby.getArchived()) {
            log.warn("Lobby with serverId {} already archived", id);
            return;
        }

        lobby.setArchived(true);
        lobby.setArchiveReason("Deleted by owner");

        log.info("Set lobby with serverId {} to archived by user {}", id, userId);
    }

    public OutLobby createLobby(CreateLobby createLobby, String userId) {
        NewGameResponse gameResponse = gameAdminAppService.createGame();

        if (gameResponse == null || gameResponse.serverId() == null || gameResponse.id() == null) {
            throw new ServiceException(String.format("Failed to create game, response: %s", gameResponse));
        }

        log.info("Received response from game-app: {id: {}, serverId: {}}", gameResponse.id(), gameResponse.serverId());

        String name = createLobby.getName();

        Optional<Lobby> existingLobby = lobbyRepository.findFirstByNameLikeOrderByCreatedAtDesc(name + "%");

        if (existingLobby.isPresent()) {
            String[] split = existingLobby.get().getName().split(" ");
            if (split[split.length - 1].matches("\\d+")) {
                int number = Integer.parseInt(split[split.length - 1]);
                split[split.length - 1] = String.valueOf(number + 1);
                name = String.join(" ", split);

                log.info("Lobby with name '{}' already exists, trying with '{}'", createLobby.getName(), name);
            } else {
                name += " 2";
            }
        }

        Lobby newLobby = Lobby.builder()
                .name(name)
                .archived(false)
                .createdByPlayerId(userId)
                .gameId(gameResponse.id())
                .serverId(gameResponse.serverId())
                .currentPlayers(0)
                .createdByPlayerId(userId)
                .maxPlayers(createLobby.getMaxPlayers())
                .build();

        newLobby.setLiveData(List.of());

        var out = toDto(lobbyRepository.save(newLobby));

        log.info("Created new lobby with serverId {} by user {}", out.getId(), userId);

        return out;
    }

    public List<OutLobby> getLobbyList() {
        return lobbyRepository.findAllLobbies().stream().map(this::toDto).toList();
    }

    private OutLobby toDto(Lobby lobby) {
        return OutLobby.builder()
                .id(lobby.getId())
                .createdAt(lobby.getCreatedAt())
                .name(lobby.getName())
                .maxPlayers(lobby.getMaxPlayers())
                .serverId(lobby.getServerId())
                .gameId(lobby.getGameId())
                .archived(lobby.getArchived())
                .owner(lobby.getCreatedByPlayerId())
                .currentPlayers(0)
                .liveData(lobby.getLiveData().stream().map(this::toStatsDto).toList())
                .build();
    }

    private OutLiveData toStatsDto(LiveData liveData) {
        return OutLiveData.builder().size(liveData.getPlayerSize()).username(liveData.getPlayerUsername()).build();
    }
}
