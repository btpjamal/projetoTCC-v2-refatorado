package dev.jamal.projetotcc.Service;

import dev.jamal.projetotcc.DTO.Recommendation.HobbyRecommendationDTO;
import dev.jamal.projetotcc.Entities.*;
import dev.jamal.projetotcc.Enum.*;
import dev.jamal.projetotcc.Exception.ResourceNotFoundException;
import dev.jamal.projetotcc.Mapper.RecommendationMapper;
import dev.jamal.projetotcc.Repository.*;
import dev.jamal.projetotcc.Service.recommendation.criteria.RecommendationCriterion;
import dev.jamal.projetotcc.Service.recommendation.model.CriterionResult;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RecommendationService {
    private final HobbyRepository hobbyRepository;
    private final RecommendationProfileRepository recommendationProfileRepository;
    private final UserInterestRepository userInterestRepository;
    private final UserHobbyFeedbackRepository feedbackRepository;
    private final RecommendationMapper mapper;
    private final List<RecommendationCriterion> criterios;

    public List<HobbyRecommendationDTO> recomendar(Long userId) {
        RecommendationProfile p = recommendationProfileRepository.findByUserId(userId).orElseThrow(() -> new ResourceNotFoundException("Complete o questionário inicial antes de receber recomendações."));
        List<UserInterest> interesses = userInterestRepository.findByUserIdWithInterest(userId);
        List<UserHobbyFeedback> feedbacks = feedbackRepository.buscarComHobbyEUsuario(userId);
        Set<Long> avaliados = feedbacks.stream().map(f -> f.getHobby().getId()).collect(Collectors.toSet());


        return hobbyRepository.findAllWithCategory().stream().filter(h -> !avaliados.contains(h.getId())).map(h -> {

            List<CriterionResult> rs = criterios.stream()
                    .map(c ->
                            c.avaliar(
                                    h,
                                    p,
                                    interesses,
                                    feedbacks
                            )
                    ).toList();

            double score = rs.stream()
                    .mapToDouble(CriterionResult::pontos)
                    .sum();

            List<String> motivos = rs.stream().flatMap(r -> r.motivos().stream()).distinct().toList();
            List<String> alertas = rs.stream().flatMap(r -> r.alertas().stream()).distinct().toList();
            return mapper.toDTO(h, score, motivos, alertas);
        }).sorted(Comparator.comparing(HobbyRecommendationDTO::getScore).reversed()).limit(20).toList();
    }
}
