package dev.jamal.projetotcc.Service;

import dev.jamal.projetotcc.DTO.Recommendation.RecommendationFeedbackResponseDTO;
import dev.jamal.projetotcc.Entities.Hobby;
import dev.jamal.projetotcc.Entities.User;
import dev.jamal.projetotcc.Entities.UserRecommendationFeedback;
import dev.jamal.projetotcc.Enum.RecommendationFeedbackType;
import dev.jamal.projetotcc.Exception.BusinessException;
import dev.jamal.projetotcc.Repository.HobbyRepository;
import dev.jamal.projetotcc.Repository.UserRecommendationFeedbackRepository;
import dev.jamal.projetotcc.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationFeedbackService {

    private final UserRepository userRepository;
    private final HobbyRepository hobbyRepository;
    private final UserRecommendationFeedbackRepository feedbackRepository;

    @Transactional
    public void registrar(
            Long userId,
            Long hobbyId,
            RecommendationFeedbackType tipo
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new BusinessException("Usuário não encontrado.")
                );

        Hobby hobby = hobbyRepository.findById(hobbyId)
                .orElseThrow(() ->
                        new BusinessException("Hobby não encontrado.")
                );

        UserRecommendationFeedback feedback =
                feedbackRepository
                        .findByUser_IdAndHobby_Id(userId, hobbyId)
                        .orElseGet(UserRecommendationFeedback::new);

        feedback.setUser(user);
        feedback.setHobby(hobby);
        feedback.setTipo(tipo);
        feedback.setCreatedAt(LocalDateTime.now());

        feedbackRepository.save(feedback);
    }

    @Transactional
    public List<RecommendationFeedbackResponseDTO> listarPorTipo(
            Long userId,
            RecommendationFeedbackType tipo
    ) {

        return feedbackRepository
                .findByUser_IdAndTipo(userId, tipo)
                .stream()
                .map(feedback -> {

                    Hobby hobby = feedback.getHobby();

                    return new RecommendationFeedbackResponseDTO(
                            hobby.getId(),
                            hobby.getNome(),
                            hobby.getDescricao(),
                            hobby.getCategory().getNome(),
                            feedback.getTipo()
                    );
                })
                .toList();
    }

    @Transactional
    public void removerDecisao(
            Long userId,
            Long hobbyId
    ) {
        UserRecommendationFeedback feedback =
                feedbackRepository
                        .findByUser_IdAndHobby_Id(userId, hobbyId)
                        .orElseThrow(() ->
                                new BusinessException(
                                        "Decisão de recomendação não encontrada."
                                )
                        );

        feedbackRepository.delete(feedback);
    }
}
