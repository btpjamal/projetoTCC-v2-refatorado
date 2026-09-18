package dev.jamal.projetotcc.DTO.Profile;

import java.util.List;

public record MyProfileResponseDTO(
        String nome,
        Integer idade,
        String cidade,
        String estado,

        String resumo,

        List<MyProfileHobbyDTO> praticando,
        List<MyProfileHobbyDTO> interessados,

        List<RecommendedHobbyDTO> recomendados,

        Double tempoDisponivelSemanal,
        Double orcamentoInicial,

        String tipoSocializacao,
        String nivelAtividadeFisicaDesejada,
        String ambientePreferido,

        List<String> interesses,
        List<String> objetivos
) {

    public record RecommendedHobbyDTO(
            Long hobbyId,
            String nome,
            Double score
    ) {
    }
}

