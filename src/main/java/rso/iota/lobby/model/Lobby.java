package rso.iota.lobby.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Builder
@Table(name = "lobbies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lobby {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(
            nullable = false
    )
    @CreationTimestamp
    OffsetDateTime createdAt;

    @Column(
            nullable = false,
            length = 100,
            unique = true
    )
    String name;

    @Column(
            nullable = false,
            length = 200
    )
    String description;

    @Column(nullable = false)
    Integer maxPlayers;
}
