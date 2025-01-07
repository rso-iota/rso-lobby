package rso.iota.lobby.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Table(name = "lobbies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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
            nullable = false
    )
    String createdByPlayerId;


    @Column(nullable = false)
    Integer maxPlayers;

    @Column(
            nullable = false
    )
    Integer currentPlayers;

    @Column(
            nullable = false
    )
    String serverId;

    @Column(
            nullable = false,
            unique = true
    )
    String gameId;

    @Column(
            nullable = false
    )
    Boolean archived;

    @Column(
            nullable = true
    )
    String archiveReason;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "lobby",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @ToString.Exclude
    private List<LiveData> liveData = new ArrayList<>();
}
