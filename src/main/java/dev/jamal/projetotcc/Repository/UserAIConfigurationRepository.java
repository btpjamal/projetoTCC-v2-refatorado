package dev.jamal.projetotcc.Repository;

import dev.jamal.projetotcc.Entities.UserAIConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAIConfigurationRepository
        extends JpaRepository<UserAIConfiguration, Long> {

    Optional<UserAIConfiguration> findByUser_Id(Long userId);

    boolean existsByUser_Id(Long userId);
}