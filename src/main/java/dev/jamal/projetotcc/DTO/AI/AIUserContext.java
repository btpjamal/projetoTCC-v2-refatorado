package dev.jamal.projetotcc.DTO.AI;

import java.util.List;

public record AIUserContext(

        UserContext usuario,
        ProfileContext perfil,
        HobbyContext hobby,
        UserHobbyContext relacaoComHobby

) {

    public record UserContext(
            Integer idade,
            String cidade,
            String estado
    ) {}

    public record ProfileContext(
            Double tempoDisponivelSemanal,
            Double orcamentoInicial,
            String tipoSocializacao,
            String nivelAtividadeFisicaDesejada,
            String ambientePreferido,
            List<String> interesses,
            List<String> objetivos
    ) {}

    public record HobbyContext(
            Long id,
            String nome,
            String descricao,
            Double custoEstimado,
            Double tempoNecessario,
            Integer nivelDificuldade,
            String categoria,
            String tipoSocializacao,
            String nivelAtividadeFisica,
            String ambiente,
            Double score,
            List<String> motivos,
            List<String> alertas
    ) {}

    public record UserHobbyContext(
            String nivelAtual,
            String statusAtual
    ) {}
}
