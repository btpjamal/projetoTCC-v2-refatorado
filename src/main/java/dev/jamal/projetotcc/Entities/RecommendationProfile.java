package dev.jamal.projetotcc.Entities;
import dev.jamal.projetotcc.Enum.TipoSocializacao;

import dev.jamal.projetotcc.Enum.*;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter
@Entity @Table(name = "recommendation_profile")
public class RecommendationProfile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double tempoDisponivelSemanal;

    @Column(nullable = false)
    private Double orcamentoInicial;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private TipoSocializacao tipoSocializacao;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private NivelAtividadeFisica nivelAtividadeFisicaDesejada;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private AmbientePreferido ambientePreferido;

    @Column(length = 100)
    private String cidade;

    @Column(length = 2)
    private String estado;

    @Column(nullable = false)
    private boolean questionarioConcluido = false;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @OneToOne @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @PrePersist
    @PreUpdate
    public void touch(){ updatedAt = LocalDateTime.now(); }
    public Double getTempoDisponivel(){ return tempoDisponivelSemanal; }
    public void setTempoDisponivel(Double valor){ this.tempoDisponivelSemanal = valor; }
    public Double getOrcamento(){ return orcamentoInicial; }
    public void setOrcamento(Double valor){ this.orcamentoInicial = valor; }
    public Boolean getQuestionarioConcluido() {
        return questionarioConcluido;
    }
    public void setQuestionarioConcluido(Boolean questionarioConcluido) {
        this.questionarioConcluido = questionarioConcluido;
    }
}
