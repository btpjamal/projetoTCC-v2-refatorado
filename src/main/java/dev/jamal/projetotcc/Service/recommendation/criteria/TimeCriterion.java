package dev.jamal.projetotcc.Service.recommendation.criteria;

import dev.jamal.projetotcc.Entities.*;
import dev.jamal.projetotcc.Service.recommendation.model.CriterionResult;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TimeCriterion implements RecommendationCriterion {

    @Override
    public CriterionResult avaliar(
            Hobby h,
            RecommendationProfile p,
            List<UserInterest> i,
            List<UserHobbyFeedback> f
    ) {
        if (h.getTempoNecessario() == null
                || p.getTempoDisponivelSemanal() == null) {
            return CriterionResult.of(0, null);
        }

        double disponivel = p.getTempoDisponivelSemanal();
        double necessario = h.getTempoNecessario();

        if (disponivel >= necessario) {
            return CriterionResult.of(
                    15,
                    "É compatível com seu tempo disponível semanal"
            );
        }

        double proporcao= disponivel/necessario;

        if (proporcao >= 0.75) {
            return CriterionResult.warning(
                    7,
                    "Pode exigir um pouco a mais do tempo que você informou"
            );
        }

        if (proporcao >= 0.5) {
            return CriterionResult.warning(
                    2,
                    "Pode ser difícil encaixar esse hobby na sua rotina atual"
            );
        }

        return CriterionResult.warning(
                -5,
                "Exige significativamente mais tempo que você possui disponível"
        );

    }
}
