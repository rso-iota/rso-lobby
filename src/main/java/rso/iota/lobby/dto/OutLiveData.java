package rso.iota.lobby.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OutLiveData {
    @NotNull
    private Float size;

    @NotNull
    private String username;
}
