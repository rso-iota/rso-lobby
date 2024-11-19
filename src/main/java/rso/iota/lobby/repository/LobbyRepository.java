package rso.iota.lobby.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import rso.iota.lobby.model.Lobby;

import java.util.UUID;

public interface LobbyRepository extends JpaRepository<Lobby, UUID>, JpaSpecificationExecutor<Lobby> {
}