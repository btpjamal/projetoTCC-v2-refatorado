package dev.jamal.projetotcc.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "general_personalized_plan",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_general_personalized_plan_user",
                        columnNames = "user_id"
                )
        }
)
@Getter
@Setter
public class GeneralPersonalizedPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            unique = true
    )
    private User user;

    @Column(
            name = "conteudo",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String conteudo;

    @Column(
            name = "context_hash",
            nullable = false,
            length = 64
    )
    private String contextHash;

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
        this.dataCriacao = agora;
        this.dataAtualizacao = agora;
    }

    @PreUpdate
    public void preUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }
}