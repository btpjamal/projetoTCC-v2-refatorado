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
                25,
                compatibilidade * 5
        );

        List<String> interessesCorrespondentes = interesses.stream()
                .filter(ui ->
                        interessesHobby.stream()
                                .anyMatch(hi ->
                                        hi.getInterest().getId()
                                                .equals(ui.getInterest().getId())
                                )
                )
                .map(ui -> ui.getInterest().getNome())
                .distinct()
                .toList();

        String motivo = interessesCorrespondentes.isEmpty()
                ? null
                : "Combina com seus interesses: "
                + String.join(", ", interessesCorrespondentes);

        return CriterionResult.of(
                pontos,
                motivo
        );
    }
}
