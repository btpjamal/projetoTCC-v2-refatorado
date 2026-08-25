package dev.jamal.projetotcc.DTO.Recommendation;

import dev.jamal.projetotcc.Enum.RecommendationFeedbackType;

public record RecommendationFeedbackResponseDTO (

    Long hobbyId,
    String nome,
    String descricao,
    String categoria,
    RecommendationFeedbackType tipo
) {}
