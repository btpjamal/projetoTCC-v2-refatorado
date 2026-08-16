package dev.jamal.projetotcc.DTO.RecommendationProfile;

import dev.jamal.projetotcc.Enum.AmbientePreferido;
import dev.jamal.projetotcc.Enum.FormatoPreferido;
import dev.jamal.projetotcc.Enum.NivelAtividadeFisica;
import dev.jamal.projetotcc.Enum.NivelExperiencia;
import dev.jamal.projetotcc.Enum.NivelSocial;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public class RecommendationProfileCreateRequestDTO {

    @NotNull
    @Positive
    private Double tempoDisponivelSemanal;

    @NotNull
    @DecimalMin("0.0")
    private Double orcamentoInicial;

    @NotNull
    private NivelSocial nivelSocial;

    @NotNull
    private NivelExperiencia nivelExperiencia;

    @NotNull
    private NivelAtividadeFisica nivelAtividadeFisicaDesejada;

    @NotNull
    private AmbientePreferido ambientePreferido;

    @NotNull
    private FormatoPreferido formatoPreferido;

    private List<Long> interestIds;

    private List<Long> objectiveIds;

    public Double getTempoDisponivelSemanal() {
        return tempoDisponivelSemanal;
    }

    public void setTempoDisponivelSemanal(
            Double tempoDisponivelSemanal
    ) {
        this.tempoDisponivelSemanal = tempoDisponivelSemanal;
    }

    public Double getOrcamentoInicial() {
        return orcamentoInicial;
    }

    public void setOrcamentoInicial(Double orcamentoInicial) {
        this.orcamentoInicial = orcamentoInicial;
    }

    public NivelSocial getNivelSocial() {
        return nivelSocial;
    }

    public void setNivelSocial(NivelSocial nivelSocial) {
        this.nivelSocial = nivelSocial;
    }

    public NivelExperiencia getNivelExperiencia() {
        return nivelExperiencia;
    }

    public void setNivelExperiencia(
            NivelExperiencia nivelExperiencia
    ) {
        this.nivelExperiencia = nivelExperiencia;
    }

    public NivelAtividadeFisica getNivelAtividadeFisicaDesejada() {
        return nivelAtividadeFisicaDesejada;
    }

    public void setNivelAtividadeFisicaDesejada(
            NivelAtividadeFisica nivelAtividadeFisicaDesejada
    ) {
        this.nivelAtividadeFisicaDesejada =
                nivelAtividadeFisicaDesejada;
    }

    public AmbientePreferido getAmbientePreferido() {
        return ambientePreferido;
    }

    public void setAmbientePreferido(
            AmbientePreferido ambientePreferido
    ) {
        this.ambientePreferido = ambientePreferido;
    }

    public FormatoPreferido getFormatoPreferido() {
        return formatoPreferido;
    }

    public void setFormatoPreferido(
            FormatoPreferido formatoPreferido
    ) {
        this.formatoPreferido = formatoPreferido;
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
}