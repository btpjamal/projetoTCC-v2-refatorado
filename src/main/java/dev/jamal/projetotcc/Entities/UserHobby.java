package dev.jamal.projetotcc.Entities;

import dev.jamal.projetotcc.Enum.NivelExperiencia;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "user_hobbies",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"user_id", "hobby_id"}
                )
        }
)
public class UserHobby {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "hobby_id",
            nullable = false
    )
    private Hobby hobby;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NivelExperiencia nivelAtual;

    @Column(nullable = false)
    private LocalDateTime criadoEm;

    @Column(nullable = false)
    private LocalDateTime atualizadoEm;

    @PrePersist
    public void prePersist() {
        LocalDateTime agora = LocalDateTime.now();

        criadoEm = agora;
        atualizadoEm = agora;
    }

    @PreUpdate
    public void preUpdate() {
        atualizadoEm = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Hobby getHobby() {
        return hobby;
    }

    public void setHobby(Hobby hobby) {
        this.hobby = hobby;
    }

    public NivelExperiencia getNivelAtual() {
        return nivelAtual;
    }

    public void setNivelAtual(NivelExperiencia nivelAtual) {
        this.nivelAtual = nivelAtual;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }
}
