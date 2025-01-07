package rso.iota.lobby;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@ConfigurationPropertiesScan
@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
public class LobbyApplication {
    public static void main(String[] args) {
        SpringApplication.run(LobbyApplication.class, args);
    }
}
