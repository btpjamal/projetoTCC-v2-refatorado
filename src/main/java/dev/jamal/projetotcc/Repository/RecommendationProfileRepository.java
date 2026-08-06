package dev.jamal.projetotcc.Repository;

import dev.jamal.projetotcc.Entities.User;
import dev.jamal.projetotcc.Entities.RecommendationProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecommendationProfileRepository extends JpaRepository<RecommendationProfile, Long> {

    Optional<RecommendationProfile> findByUserId(Long userId);

    Long user(User user);
}
