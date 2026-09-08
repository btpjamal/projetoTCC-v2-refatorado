package dev.jamal.projetotcc.Service.AI;

import dev.jamal.projetotcc.DTO.AI.AIGeneralPlanContext;
import dev.jamal.projetotcc.DTO.Recommendation.HobbyRecommendationDTO;
import dev.jamal.projetotcc.Entities.*;
import dev.jamal.projetotcc.Enum.RecommendationFeedbackType;
import dev.jamal.projetotcc.Enum.UserHobbyStatus;
import dev.jamal.projetotcc.Exception.BusinessException;
import dev.jamal.projetotcc.Repository.*;
import dev.jamal.projetotcc.Service.RecommendationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.List;

@Service
public class AIGeneralPlanContextService {

    private static final int MAX_HOBBIES_GENERAL_PLAN = 10;

    private final UserRepository userRepository;
    private final RecommendationProfileRepository profileRepository;
    private final UserRecommendationFeedbackRepository feedbackRepository;
    private final UserHobbyRepository userHobbyRepository;
    private final UserInterestRepository userInterestRepository;
    private final UserObjectiveRepository userObjectiveRepository;
    private final RecommendationService recommendationService;

    public AIGeneralPlanContextService(
            UserRepository userRepository,
            RecommendationProfileRepository profileRepository,
            UserRecommendationFeedbackRepository feedbackRepository,
            UserHobbyRepository userHobbyRepository,
            UserInterestRepository userInterestRepository,
            UserObjectiveRepository userObjectiveRepository,
            RecommendationService recommendationService
    ) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.feedbackRepository = feedbackRepository;
        this.userHobbyRepository = userHobbyRepository;
        this.userInterestRepository = userInterestRepository;
        this.userObjectiveRepository = userObjectiveRepository;
        this.recommendationService = recommendationService;
    }

    public AIGeneralPlanContext construir(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new BusinessException("Usuário não encontrado.")
                );

        RecommendationProfile profile =
                profileRepository.findByUserId(userId)
                        .orElseThrow(() ->
                                new BusinessException(
                                        "Perfil de recomendação não encontrado."
                                )
                        );

        List<String> interesses =
                userInterestRepository.findByUserIdWithInterest(userId)
                        .stream()
                        .map(userInterest ->
                                userInterest.getInterest().getNome()
                        )
                        .sorted()
                        .toList();

        List<String> objetivos =
                userObjectiveRepository.findByUserId(userId)
                        .stream()
                        .map(userObjective ->
                                userObjective.getObjective().getNome()
                        )
                        .sorted()
                        .toList();

        List<AIGeneralPlanContext.HobbySummaryContext> hobbies =
                feedbackRepository
                        .findByUser_IdAndTipo(
                                userId,
                                RecommendationFeedbackType.INTERESSADO
                        )
                        .stream()
                        .map(feedback ->
                                montarHobbyContext(
                                        userId,
                                        feedback.getHobby()
                                )
                        )
                        .sorted(
                                Comparator
                                        .comparingInt(
                                                this::prioridadeStatus
                                        )
                                        .thenComparing(
                                                AIGeneralPlanContext
                                                        .HobbySummaryContext
                                                        ::score,
                                                Comparator.nullsLast(
                                                        Comparator.reverseOrder()
                                                )
                                        )
                        )
                        .limit(MAX_HOBBIES_GENERAL_PLAN)
                        .toList();

        if (hobbies.isEmpty()) {
            throw new BusinessException(
                    "Você ainda não possui hobbies marcados como interessado."
            );
        }

        return new AIGeneralPlanContext(

                new AIGeneralPlanContext.UserContext(
                        calcularIdade(user.getDataNascimento()),
                        profile.getCidade(),
                        profile.getEstado()
                ),

                new AIGeneralPlanContext.ProfileContext(
                        profile.getTempoDisponivelSemanal(),
                        profile.getOrcamentoInicial(),
                        profile.getTipoSocializacao().name(),
                        profile.getNivelAtividadeFisicaDesejada().name(),
                        profile.getAmbientePreferido().name(),
                        interesses,
                        objetivos
                ),

                hobbies
        );
    }

    private AIGeneralPlanContext.HobbySummaryContext montarHobbyContext(
            Long userId,
            Hobby hobby
    ) {

        UserHobby userHobby =
                userHobbyRepository
                        .findByUser_IdAndHobby_Id(
                                userId,
                                hobby.getId()
                        )
                        .orElse(null);

        HobbyRecommendationDTO recomendacao =
                recommendationService.calcularRecomendacao(
                        userId,
                        hobby.getId()
                );

        return new AIGeneralPlanContext.HobbySummaryContext(
                hobby.getId(),
                hobby.getNome(),
                hobby.getCategory().getNome(),
                hobby.getTempoNecessario(),
                hobby.getNivelAtividadeFisica().name(),
                hobby.getAmbiente().name(),
                recomendacao.getScore(),
                userHobby != null
                        ? userHobby.getNivelAtual().name()
                        : null,
                userHobby != null
                        ? userHobby.getStatusAtual().name()
                        : null
        );
    }

    private int prioridadeStatus(
            AIGeneralPlanContext.HobbySummaryContext hobby
    ) {

        if (UserHobbyStatus.PRATICANDO.name()
                .equals(hobby.statusAtual())) {
            return 0;
        }

        if (UserHobbyStatus.INTERESSADO.name()
                .equals(hobby.statusAtual())) {
            return 1;
        }

        if (UserHobbyStatus.PAUSADO.name()
                .equals(hobby.statusAtual())) {
            return 2;
        }

        return 3;
    }

    private Integer calcularIdade(LocalDate dataNascimento) {

        if (dataNascimento == null) {
            return null;
        }

        return Period.between(
                dataNascimento,
                LocalDate.now()
        ).getYears();
    }
}