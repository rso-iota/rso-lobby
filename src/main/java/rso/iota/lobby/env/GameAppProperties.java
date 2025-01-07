package rso.iota.lobby.env;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "svc.game-app")
public record GameAppProperties(@NotNull @NotBlank @URL String adminUrl, @NotNull @NotBlank String grpcSvcTemplate) {
}


