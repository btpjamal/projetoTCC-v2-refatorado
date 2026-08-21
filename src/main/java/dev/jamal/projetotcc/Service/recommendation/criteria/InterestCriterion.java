package dev.jamal.projetotcc.Service.recommendation.criteria;

import dev.jamal.projetotcc.Entities.*;
import dev.jamal.projetotcc.Repository.HobbyInterestRepository;
import dev.jamal.projetotcc.Service.recommendation.model.CriterionResult;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InterestCriterion implements RecommendationCriterion {

    private final HobbyInterestRepository hobbyInterestRepository;

    public InterestCriterion(HobbyInterestRepository hobbyInterestRepository) {
        this.hobbyInterestRepository = hobbyInterestRepository;
    }

    @Override
    public CriterionResult avaliar(
            Hobby hobby,
            RecommendationProfile perfil,
            List<UserInterest> interesses,
            List<UserHobbyFeedback> feedbacks
    ) {
        if (interesses == null || interesses.isEmpty()) {
            return CriterionResult.of(0, null);
        }

        List<HobbyInterest> interessesHobby=
                hobbyInterestRepository.findByHobbyId(hobby.getId());

        if (interessesHobby.isEmpty()) {
            return CriterionResult.of(0,null);
        }

        int compatibilidade = interesses.stream()
                .mapToInt(userInterest ->
                        interessesHobby.stream()
                                .filter(hobbyInterest ->
                                        hobbyInterest
                                                .getInterest()
                                                .getId()
                                                .equals(
                                                        userInterest
                                                                .getInterest()
                                                                .getId()
                                                )
                                )
                                .mapToInt(hobbyInterest -> {
                                    int pesoHobby =
                                            hobbyInterest.getPeso() != null
                                                    ? hobbyInterest.getPeso()
                                                    : 1;

                                    int pesoUsuario =
                                            userInterest.getPeso() != null
                                                    ? userInterest.getPeso()
                                                    : 1;

                                    return pesoHobby * pesoUsuario;
                                })
                                .max()
                                .orElse(0)
                )
                .sum();

        if (compatibilidade == 0) {
            return CriterionResult.of(0, null);
        }

        double pontos = Math.min(
                30,
                compatibilidade * 5
        );

        return CriterionResult.of(
                pontos,
                "Este hobby combina com áreas que despertam seu interesse"
        );
    }
}
