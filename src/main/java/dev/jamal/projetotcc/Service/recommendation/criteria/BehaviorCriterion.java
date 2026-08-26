package dev.jamal.projetotcc.Service.recommendation.criteria;

import dev.jamal.projetotcc.Entities.*;
import dev.jamal.projetotcc.Enum.RecommendationFeedbackType;
import dev.jamal.projetotcc.Repository.HobbyInterestRepository;
import dev.jamal.projetotcc.Repository.UserRecommendationFeedbackRepository;
import dev.jamal.projetotcc.Service.recommendation.model.CriterionResult;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BehaviorCriterion implements RecommendationCriterion {

    private final UserRecommendationFeedbackRepository feedbackRepository;
    private final HobbyInterestRepository hobbyInterestRepository;

    public BehaviorCriterion(
            UserRecommendationFeedbackRepository feedbackRepository,
            HobbyInterestRepository hobbyInterestRepository
    ) {
        this.feedbackRepository = feedbackRepository;
        this.hobbyInterestRepository = hobbyInterestRepository;
    }

    @Override
    public CriterionResult avaliar(
            Hobby hobby,
            RecommendationProfile perfil,
            List<UserInterest> interesses,
            List<UserHobbyFeedback> feedbacks
    ) {

        Long userId = perfil.getUser().getId();

        List<UserRecommendationFeedback> decisoes =
                feedbackRepository.findByUser_Id(userId);

        if (decisoes.isEmpty()) {
            return CriterionResult.of(0, null);
        }

        List<HobbyInterest> interessesCandidato =
                hobbyInterestRepository.findByHobbyId(hobby.getId());

        if (interessesCandidato.isEmpty()) {
            return CriterionResult.of(0, null);
        }

        double sinal = 0;

        for (UserRecommendationFeedback decisao : decisoes) {

            List<HobbyInterest> interessesHistorico =
                    hobbyInterestRepository.findByHobbyId(
                            decisao.getHobby().getId()
                    );

            for (HobbyInterest historico : interessesHistorico) {

                for (HobbyInterest candidato : interessesCandidato) {

                    if (!historico.getInterest().getId()
                            .equals(candidato.getInterest().getId())) {
                        continue;
                    }

                    int pesoHistorico =
                            historico.getPeso() != null
                                    ? historico.getPeso()
                                    : 1;

                    int pesoCandidato =
                            candidato.getPeso() != null
                                    ? candidato.getPeso()
                                    : 1;

                    double similaridade =
                            pesoHistorico * pesoCandidato;

                    if (decisao.getTipo()
                            == RecommendationFeedbackType.INTERESSADO) {

                        sinal += similaridade * 0.10;

                    } else if (decisao.getTipo()
                            == RecommendationFeedbackType.NAO_INTERESSADO) {

                        sinal -= similaridade * 0.15;
                    }
                }
            }
        }

        // Converte a afinidade comportamental
        // em um modificador do score.
        double pontos = sinal * 5;

        // Impede o comportamento de dominar
        // o perfil declarado pelo usuário.
        pontos = Math.max(
                -15,
                Math.min(15, pontos)
        );

        if (pontos > 0) {
            return CriterionResult.of(
                    pontos,
                    "Seu histórico indica interesse por atividades semelhantes"
            );
        }

        if (pontos < 0) {
            return CriterionResult.warning(
                    pontos,
                    "Seu histórico indica menor interesse por atividades semelhantes"
            );
        }

        return CriterionResult.of(0, null);
    }
}