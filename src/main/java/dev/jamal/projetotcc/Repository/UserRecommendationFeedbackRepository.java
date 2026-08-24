package dev.jamal.projetotcc.Repository;

import dev.jamal.projetotcc.Entities.UserRecommendationFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRecommendationFeedbackRepository
        extends JpaRepository <UserRecommendationFeedback, Long> {

    Optional<UserRecommendationFeedback> findByUser_IdAndHobby_Id(
            Long userId,
            Long HobbyId
    );

    List<UserRecommendationFeedback> findByUserId(Long userId);
}
