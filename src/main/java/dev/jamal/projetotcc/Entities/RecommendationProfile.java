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

    @Enumerated(EnumType.STRING)
    private NivelSocial nivelSocial;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private TipoSocializacao tipoSocializacao;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private NivelAtividadeFisica nivelAtividadeFisicaDesejada;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private AmbientePreferido ambientePreferido;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private FormatoPreferido formatoPreferido;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private NivelExperiencia nivelExperiencia;

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
    // Compatibilidade temporária com rotinas antigas; remover após migração completa.
    public Double getTempoDisponivel(){ return tempoDisponivelSemanal; }
    public void setTempoDisponivel(Double valor){ this.tempoDisponivelSemanal = valor; }
    public Double getOrcamento(){ return orcamentoInicial; }
    public void setOrcamento(Double valor){ this.orcamentoInicial = valor; }
    public dev.jamal.projetotcc.Enum.NivelSocial getNivelSocial() {
        return tipoSocializacao == TipoSocializacao.SOCIAL ? dev.jamal.projetotcc.Enum.NivelSocial.EXTROVERTIDO : dev.jamal.projetotcc.Enum.NivelSocial.INTROVERTIDO;
    }
    public void setNivelSocial(dev.jamal.projetotcc.Enum.NivelSocial valor){
        this.tipoSocializacao = valor == dev.jamal.projetotcc.Enum.NivelSocial.EXTROVERTIDO ? TipoSocializacao.SOCIAL : TipoSocializacao.INDIVIDUAL;
        if (nivelAtividadeFisicaDesejada == null) nivelAtividadeFisicaDesejada = NivelAtividadeFisica.INDIFERENTE;
        if (ambientePreferido == null) ambientePreferido = AmbientePreferido.INDIFERENTE;
        if (formatoPreferido == null) formatoPreferido = FormatoPreferido.INDIFERENTE;
        if (nivelExperiencia == null) nivelExperiencia = NivelExperiencia.INICIANTE;
    }

    public Boolean getQuestionarioConcluido() {
        return questionarioConcluido;
    }
    public void setQuestionarioConcluido(Boolean questionarioConcluido) {
        this.questionarioConcluido = questionarioConcluido;
    }
}
