package dev.jamal.projetotcc.Entities;

import dev.jamal.projetotcc.Enum.CommunityType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "communities",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_community_hobby_type_location",
                        columnNames = {
                                "hobby_id",
                                "tipo",
                                "estado",
                                "cidade"
                        }
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hobby_id", nullable = false)
    private Hobby hobby;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CommunityType tipo;

    @Column(length = 100)
    private String estado;

    @Column(length = 150)
    private String cidade;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    @PrePersist
    public void prePersist() {
        dataCriacao = LocalDateTime.now();
    }
}