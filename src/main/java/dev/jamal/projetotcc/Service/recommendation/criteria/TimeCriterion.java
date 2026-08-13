package dev.jamal.projetotcc.Service.recommendation.criteria;

import dev.jamal.projetotcc.Entities.*;
import dev.jamal.projetotcc.Service.recommendation.model.CriterionResult;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TimeCriterion implements RecommendationCriterion {
    public CriterionResult avaliar(Hobby h, RecommendationProfile p, List<UserInterest> i, List<UserHobbyFeedback> f) {
        if (h.getTempoNecessario() <= p.getTempoDisponivelSemanal())
            return CriterionResult.of(15, "É compatível com seu tempo semanal");
        return CriterionResult.warning(-4, "Pode exigir mais tempo do que você informou");
    }
}
