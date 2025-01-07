package rso.iota.lobby.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Builder
@Table(
        name = "live_data",
        indexes = {@Index(
                name = "idx_livedata_lobby_id",
                columnList = "lobby_id"
        )}
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LiveData {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(
            nullable = false
    )
    @CreationTimestamp
    OffsetDateTime createdAt;

    @Column(
            nullable = false
    )
    String playerUsername;

    @Column(
            nullable = false
    )
    Float playerSize;

    @ManyToOne
    @JoinColumn(
            name = "lobby_id",
            nullable = false
    )
    @ToString.Exclude
    private Lobby lobby;
}
