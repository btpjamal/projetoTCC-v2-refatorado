package dev.jamal.projetotcc.DTO.RecommendationProfile;

import dev.jamal.projetotcc.Enum.AmbientePreferido;
import dev.jamal.projetotcc.Enum.FormatoPreferido;
import dev.jamal.projetotcc.Enum.NivelAtividadeFisica;
import dev.jamal.projetotcc.Enum.NivelExperiencia;
import dev.jamal.projetotcc.Enum.NivelSocial;

import java.util.List;

public class RecommendationProfileResponseDTO {

    private Long id;
    private Long userId;
    private Double tempoDisponivelSemanal;
    private Double orcamentoInicial;
    private NivelSocial nivelSocial;
    private NivelExperiencia nivelExperiencia;
    private NivelAtividadeFisica nivelAtividadeFisicaDesejada;
    private AmbientePreferido ambientePreferido;
    private FormatoPreferido formatoPreferido;
    private Boolean questionarioConcluido;
    private List<Long> interestIds;
    private List<Long> objectiveIds;
    private String cidade;
    private String estado;

    public RecommendationProfileResponseDTO(
            Long id,
            Long userId,
            Double tempoDisponivelSemanal,
            Double orcamentoInicial,
            NivelSocial nivelSocial,
            NivelExperiencia nivelExperiencia,
            NivelAtividadeFisica nivelAtividadeFisicaDesejada,
            AmbientePreferido ambientePreferido,
            FormatoPreferido formatoPreferido,
            Boolean questionarioConcluido,
            List interestIds,
            List objectiveIds,
            String cidade,
            String estado
    ) {
        this.id = id;
        this.userId = userId;
        this.tempoDisponivelSemanal = tempoDisponivelSemanal;
        this.orcamentoInicial = orcamentoInicial;
        this.nivelSocial = nivelSocial;
        this.nivelExperiencia = nivelExperiencia;
        this.nivelAtividadeFisicaDesejada = nivelAtividadeFisicaDesejada;
        this.ambientePreferido = ambientePreferido;
        this.formatoPreferido = formatoPreferido;
        this.questionarioConcluido = questionarioConcluido;
        this.interestIds = interestIds;
        this.objectiveIds = objectiveIds;
        this.cidade = cidade;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Double getTempoDisponivelSemanal() {
        return tempoDisponivelSemanal;
    }

    public Double getOrcamentoInicial() {
        return orcamentoInicial;
    }

    public NivelSocial getNivelSocial() {
        return nivelSocial;
    }

    public NivelExperiencia getNivelExperiencia() {
        return nivelExperiencia;
    }

    public NivelAtividadeFisica getNivelAtividadeFisicaDesejada() {
        return nivelAtividadeFisicaDesejada;
    }

    public AmbientePreferido getAmbientePreferido() {
        return ambientePreferido;
    }

    public FormatoPreferido getFormatoPreferido() {
        return formatoPreferido;
    }

    public Boolean getQuestionarioConcluido() {
        return questionarioConcluido;
    }

    public List<Long> getInterestIds() {
        return interestIds;
    }

    public void setInterestIds(List<Long> interestIds) {
        this.interestIds = interestIds;
    }

    public List<Long> getObjectiveIds() {
        return objectiveIds;
    }

    public void setObjectiveIds(List<Long> objectiveIds) {
        this.objectiveIds = objectiveIds;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}