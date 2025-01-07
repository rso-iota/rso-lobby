package rso.iota.lobby.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.UnavailableException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import rso.iota.lobby.env.GameAppProperties;
import rso.iota.lobby.service.domain.NewGameResponse;


@RequiredArgsConstructor
@Service
@Slf4j
public class GameAdminAppService {
    private final WebClient webClient;
    private final GameAppProperties gameAppProperties;


    @PostConstruct
    public void init() {
        log.info("Game admin service initialized with url: {}", gameAppProperties.adminUrl());

    }


    @CircuitBreaker(
            name = "GameAppAdmin",
            fallbackMethod = "createGameFallback"
    )
    public NewGameResponse createGame() {
        return webClient.post()
                .uri(gameAppProperties.adminUrl() + "/game")
                .retrieve()
                .bodyToMono(NewGameResponse.class)
                .block();
    }

    public ResponseEntity<Void> deleteGame(String serverId, String gameId) {
        String url = gameAppProperties.adminUrl() + String.format("/game?id=%s", gameId);

        if (url.contains("{SERVER_ID}")) {
            url = url.replace("{SERVER_ID}", serverId);
        } else {
            log.warn("{SERVER_ID} not found in URL template. Are you sure it's correct?");
        }
        log.info("Deleting game with serverId {} using base url {}", serverId, url);

        return webClient.delete().uri(url).retrieve().toBodilessEntity().block();
    }

    public NewGameResponse createGameFallback(Exception e) throws UnavailableException {
        throw new UnavailableException("Game app is not available. Circuit breaker triggered");
    }
}

