package rso.iota.lobby;

import org.junit.jupiter.api.Test;

class LobbyApplicationTests {

    @Test
    void apacheVelocity() {
        String template = "Url = $HOSTNAME:$PORT-com";

        String hostname = "localhost";
        String port = "8080";
        String result = template.replace("$HOSTNAME", hostname).replace("$PORT", port);
    }
}

