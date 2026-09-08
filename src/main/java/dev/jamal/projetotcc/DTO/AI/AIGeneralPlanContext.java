package dev.jamal.projetotcc.DTO.AI;

import java.util.List;

public record AIGeneralPlanContext(

        UserContext usuario,
        ProfileContext perfil,
        List<HobbySummaryContext> hobbies

) {

    public record UserContext(
            Integer idade,
            String cidade,
            String estado
    ) {
    }

    public record ProfileContext(
            Double tempoDisponivelSemanal,
            Double orcamentoInicial,
            String tipoSocializacao,
            String nivelAtividadeFisicaDesejada,
            String ambientePreferido,
            List<String> interesses,
            List<String> objetivos
    ) {
    }

    public record HobbySummaryContext(
            Long hobbyId,
            String nome,
            String categoria,
            Double tempoNecessario,
            String nivelAtividadeFisica,
            String ambiente,
            Double score,
            String nivelAtual,
            String statusAtual
    ) {
    }
}