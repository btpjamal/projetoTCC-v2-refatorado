package dev.jamal.projetotcc.Service.recommendation.model;

import java.util.List;

public record CriterionResult(double pontos, List<String> motivos, List<String> alertas) {
    public static CriterionResult of(double pontos, String motivo) {
        return new CriterionResult(pontos, motivo == null ? List.of() : List.of(motivo), List.of());
    }

    public static CriterionResult warning(double pontos, String alerta) {
        return new CriterionResult(pontos, List.of(), alerta == null ? List.of() : List.of(alerta));
    }
}
