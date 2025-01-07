package rso.iota.lobby.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import rso.iota.lobby.model.Lobby;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LobbyRepository extends JpaRepository<Lobby, UUID>, JpaSpecificationExecutor<Lobby> {
    Optional<Lobby> findFirstByNameLikeOrderByCreatedAtDesc(String name);

    Lobby findLobbyByGameId(String gameId);


    @EntityGraph(attributePaths = {"liveData"})
    @Query("SELECT L FROM Lobby L ORDER BY L.archived ASC, L.createdAt DESC")
    List<Lobby> findAllLobbies();
}