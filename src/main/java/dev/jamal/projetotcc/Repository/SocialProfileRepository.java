package dev.jamal.projetotcc.Repository;

import dev.jamal.projetotcc.Entities.SocialProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SocialProfileRepository
        extends JpaRepository<SocialProfile, Long> {

    Optional<SocialProfile> findByUser_Id(Long userId);

    boolean existsByUser_Id(Long userId);

    List<SocialProfile> findByEnabledTrue();
}