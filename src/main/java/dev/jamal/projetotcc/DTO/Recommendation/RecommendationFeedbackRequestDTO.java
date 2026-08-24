package dev.jamal.projetotcc.DTO.Recommendation;

import dev.jamal.projetotcc.Enum.RecommendationFeedbackType;
import jakarta.persistence.GeneratedValue;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecommendationFeedbackRequestDTO {

    private RecommendationFeedbackType tipo;

}
