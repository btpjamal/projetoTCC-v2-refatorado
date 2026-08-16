package dev.jamal.projetotcc.Service.recommendation.criteria;

import dev.jamal.projetotcc.Entities.*;
import dev.jamal.projetotcc.Repository.HobbyObjectiveRepository;
import dev.jamal.projetotcc.Repository.UserObjectiveRepository;
import dev.jamal.projetotcc.Service.recommendation.model.CriterionResult;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ObjectiveCriterion implements RecommendationCriterion{

    private final UserObjectiveRepository userObjectiveRepository;
    private final HobbyObjectiveRepository hobbyObjectiveRepository;


    public ObjectiveCriterion(
            UserObjectiveRepository userObjectiveRepository,
            HobbyObjectiveRepository hobbyObjectiveRepository
    ) {
        this.userObjectiveRepository = userObjectiveRepository;
        this.hobbyObjectiveRepository = hobbyObjectiveRepository;
    }

    @Override
    public CriterionResult avaliar(
            Hobby hobby,
            RecommendationProfile perfil,
            List<UserInterest> interesses,
            List<UserHobbyFeedback> feedbacks
    ) {
        Long userId = perfil.getUser().getId();

        List<UserObjective> objetivosUsuario =
                userObjectiveRepository.findByUserId(userId);

        List<HobbyObjective> objetivosHobby =
                hobbyObjectiveRepository.findByHobbyId(hobby.getId());

        long compatibilidade = objetivosUsuario.stream()
                .filter(uo ->
                        objetivosHobby.stream()
                                .anyMatch(ho ->
                                        ho.getObjective()
                                                .getId()
                                                .equals(
                                                        uo.getObjective().getId()
                                                )
                                )
                )
                .count();

        if (compatibilidade == 0) {
            return CriterionResult.of(0, null);
        }

        double pontos = Math.min(
                25,
                compatibilidade * 10
        );

        return CriterionResult.of(
                pontos,
                "Este hobby combina com seus objetivos pessoais"
        );
    }
}
