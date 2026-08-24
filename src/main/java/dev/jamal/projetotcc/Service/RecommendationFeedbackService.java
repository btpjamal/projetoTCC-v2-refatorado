package dev.jamal.projetotcc.Service;

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
}
