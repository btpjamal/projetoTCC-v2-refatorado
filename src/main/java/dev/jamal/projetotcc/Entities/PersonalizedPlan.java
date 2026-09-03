package dev.jamal.projetotcc.Entities;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "personalized_plans",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_personalized_plan_user_hobby",
                        columnNames = {"user_id", "hobby_id"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
public class PersonalizedPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "hobby_id",
            nullable = false
    )
    private Hobby hobby;

    @Column(
            name = "context_hash",
            nullable = false,
            length = 64
    )
    private String contextHash;

    @Column(
            name = "conteudo",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String conteudo;

    @Column(
            name = "data_criacao",
            nullable = false
    )
    private LocalDateTime dataCriacao;

    @Column(
            name = "data_atualizacao",
            nullable = false
    )
    private LocalDateTime dataAtualizacao;

    @PrePersist
    public void prePersist() {
        LocalDateTime agora = LocalDateTime.now();

        dataCriacao = agora;
        dataAtualizacao = agora;
    }

    @PreUpdate
    public void preUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }
}
