package rso.iota.lobby.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateLobby {
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @Min(2)
    @Max(10)
    private Integer maxPlayers;
}
