package dev.jamal.projetotcc.Repository;

import dev.jamal.projetotcc.Entities.GeneralPersonalizedPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GeneralPersonalizedPlanRepository
        extends JpaRepository<GeneralPersonalizedPlan, Long> {

    Optional<GeneralPersonalizedPlan> findByUser_Id(Long userId);

    boolean existsByUser_Id(Long userId);
}