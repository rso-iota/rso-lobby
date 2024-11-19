package rso.iota.lobby.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rso.iota.lobby.common.annotation.*;
import rso.iota.lobby.dto.CreateLobby;
import rso.iota.lobby.dto.OutLobby;
import rso.iota.lobby.service.LobbyService;

import java.util.List;
import java.util.UUID;


@ControllerCommon(
        path = "/api/v1/lobby",
        tag = "Lobby",
        description = "Lobby management"
)
@AllArgsConstructor
public class LobbyController {

    private final LobbyService lobbyService;

    @GetMapping
    @Operation(
            summary = "Get lobby list",
            description = "Get list of all lobbies"
    )
    @Api200(description = "List of lobbies")
    public ResponseEntity<List<OutLobby>> getLobbyList() {
        return ResponseEntity.ok(lobbyService.getLobbyList());
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create lobby",
            description = "Create new lobby"
    )
    @Api201(description = "Created lobby")
    @Api400(description = "Invalid input")
    @Api409(description = "Lobby with this name already exists")
    public ResponseEntity<OutLobby> createLobby(@Valid @RequestBody CreateLobby createLobby) {
        return ResponseEntity.ok(lobbyService.createLobby(createLobby));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Get lobby",
            description = "Get lobby by ID"
    )
    @Parameter(
            name = "id",
            description = "Lobby ID",
            required = true,
            example = "123e4567-e89b-12d3-a456-426614174000"
    )
    @Api404(description = "Lobby not found")
    public ResponseEntity<Void> deleteLobby(@PathVariable UUID id) {
        lobbyService.deleteLobby(id);
        return ResponseEntity.noContent().build();
    }
}
