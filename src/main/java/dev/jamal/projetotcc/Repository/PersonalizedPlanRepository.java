package dev.jamal.projetotcc.Repository;

import dev.jamal.projetotcc.Entities.PersonalizedPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonalizedPlanRepository
        extends JpaRepository<PersonalizedPlan, Long> {

    Optional<PersonalizedPlan> findByUser_IdAndHobby_Id(
            Long userId,
            Long hobbyId
    );

    boolean existsByUser_IdAndHobby_Id(
            Long userId,
            Long hobbyId
    );
}