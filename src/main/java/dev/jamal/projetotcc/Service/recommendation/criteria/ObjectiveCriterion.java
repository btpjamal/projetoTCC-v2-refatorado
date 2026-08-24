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

        if (objetivosUsuario.isEmpty() || objetivosHobby.isEmpty()) {
            return CriterionResult.of(0, null);
        }

        int compatibilidade = objetivosUsuario.stream()
                .mapToInt(uo ->
                        objetivosHobby.stream()
                                .filter(ho ->
                                        ho.getObjective()
                                                .getId()
                                                .equals(
                                                        uo.getObjective()
                                                                .getId()
                                                )
                                )
                                .mapToInt(ho -> {

                                    int pesoHobby =
                                            ho.getPeso() != null
                                                    ? ho.getPeso()
                                                    : 1;

                                    int pesoUsuario =
                                            uo.getPeso() != null
                                                    ? uo.getPeso()
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

        List<String> objetivosCorrespondentes = objetivosUsuario.stream()
                .filter(uo ->
                        objetivosHobby.stream()
                                .anyMatch(ho ->
                                        ho.getObjective().getId()
                                                .equals(uo.getObjective().getId())
                                )
                )
                .map(uo -> uo.getObjective().getNome())
                .distinct()
                .toList();

        String motivo = objetivosCorrespondentes.isEmpty()
                ? null
                : "Ajuda nos seus objetivos: "
                + String.join(", ", objetivosCorrespondentes);

        return CriterionResult.of(
                pontos,
                motivo
        );
    }
}
