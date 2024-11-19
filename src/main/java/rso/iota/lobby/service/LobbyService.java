package rso.iota.lobby.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rso.iota.lobby.dto.CreateLobby;
import rso.iota.lobby.dto.OutLobby;
import rso.iota.lobby.dto.error.NotFoundException;
import rso.iota.lobby.model.Lobby;
import rso.iota.lobby.repository.LobbyRepository;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class LobbyService {
    private final LobbyRepository lobbyRepository;

    public void deleteLobby(UUID id) {
        Lobby lobby = lobbyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Lobby with id %s not found", id)));

        lobbyRepository.deleteById(lobby.getId());
    }

    public OutLobby createLobby(CreateLobby createLobby) {
        Lobby newLobby = Lobby.builder()
                .name(createLobby.getName())
                .description(createLobby.getDescription())
                .maxPlayers(createLobby.getMaxPlayers())
                .build();

        return toDto(lobbyRepository.save(newLobby));
    }

    public List<OutLobby> getLobbyList() {
        return lobbyRepository.findAll().stream().map(this::toDto).toList();
    }

    private OutLobby toDto(Lobby lobby) {
        return OutLobby.builder()
                .id(lobby.getId())
                .name(lobby.getName())
                .description(lobby.getDescription())
                .maxPlayers(lobby.getMaxPlayers())
                .currentPlayers(0)
                .owner("todo")
                .createdAt(lobby.getCreatedAt())
                .build();
    }
}
