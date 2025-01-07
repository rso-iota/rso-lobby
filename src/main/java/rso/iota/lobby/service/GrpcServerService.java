package rso.iota.lobby.service;

import io.grpc.stub.StreamObserver;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;
import net.devh.boot.grpc.server.service.GrpcService;
import rso.iota.lobby.model.LiveData;
import rso.iota.lobby.model.Lobby;
import rso.iota.lobby.repository.LobbyRepository;
import rso_comms.LobbyServiceGrpc;
import rso_comms.LobbySvc;

import java.util.ArrayList;

@GrpcService
@Slf4j
@RequiredArgsConstructor
public class GrpcServerService extends LobbyServiceGrpc.LobbyServiceImplBase {

    private final LobbyRepository lobbyRepository;


    @Override
    public void deleteGame(LobbySvc.EndGameRequest request, StreamObserver<LobbySvc.GameID> responseObserver) {
        String gameId = request.getId().getId();

        Lobby lobbyDb = findByGameId(gameId);

        if (lobbyDb.getArchived()) {
            log.warn("Lobby with gameId {} already archived", gameId);
        } else {
            lobbyDb.setArchived(true);

            switch (request.getReason()) {
                case WIN -> {
                    lobbyDb.setArchiveReason("Game ended with a win");
                }
                case INACTIVITY -> {
                    lobbyDb.setArchiveReason("Game ended due to inactivity");
                }
                case UNRECOGNIZED -> {
                    lobbyDb.setArchiveReason("Game ended for an unknown reason");
                }
            }

            lobbyRepository.save(lobbyDb);

            log.info("Lobby with gameId {} archived with reason {} by the game server", gameId, request.getReason());
        }


        LobbySvc.GameID gameID = LobbySvc.GameID.newBuilder().setId(gameId).build();

        responseObserver.onNext(gameID);
        responseObserver.onCompleted();
    }

    /**
     * Updated the live data of the game: Number of players and who has the biggest size
     *
     * @param request          ID and live data
     * @param responseObserver Game ID
     */
    @Override
    @GrpcExceptionHandler
    @Transactional
    public void updateLiveData(LobbySvc.LiveDataRequest request, StreamObserver<LobbySvc.GameID> responseObserver) {
        String gameId = request.getId().getId();

        Lobby lobbyDb = findByGameId(gameId);

        if (lobbyDb.getArchived()) {
            log.warn("Lobby with gameId {} already archived. This shouldn't get updated anymore", gameId);
        }

        ArrayList<LiveData> newLiveData = new ArrayList<>(request.getPlayersList()
                .stream()
                .map(msg -> LiveData.builder()
                        .lobby(lobbyDb)
                        .playerSize(msg.getSize())
                        .playerUsername(msg.getUsername())
                        .build())
                .toList());

        lobbyDb.getLiveData().clear();
        lobbyDb.getLiveData().addAll(newLiveData);
        lobbyDb.setCurrentPlayers(request.getPlayersList().size());

        lobbyRepository.save(lobbyDb);

        LobbySvc.GameID gameID = LobbySvc.GameID.newBuilder().setId(gameId).build();

        responseObserver.onNext(gameID);
        responseObserver.onCompleted();
    }

    private Lobby findByGameId(String gameId) {
        Lobby lobby = lobbyRepository.findLobbyByGameId(gameId);

        if (lobby == null) {
            log.warn("Lobby with gameId {} not found", gameId);

            throw new IllegalArgumentException("Lobby with gameId " + gameId + " not found");
        }

        return lobby;
    }
}