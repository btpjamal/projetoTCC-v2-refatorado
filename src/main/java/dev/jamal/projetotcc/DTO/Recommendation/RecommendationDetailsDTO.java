package dev.jamal.projetotcc.DTO.Recommendation;

import java.time.LocalDateTime;
import java.util.List;

public record RecommendationDetailsDTO(

        Long hobbyId,
        String nome,
        String descricao,
        String categoria,

        Double score,
        List<String> motivos,
        List<String> alertas,

        String nivelAtual,
        String statusAtual,

        PlanoResumoDTO plano

) {

    public record PlanoResumoDTO(
            boolean existe,
            String conteudo,
            boolean stale,
            LocalDateTime dataAtualizacao
    ) {
    }
}