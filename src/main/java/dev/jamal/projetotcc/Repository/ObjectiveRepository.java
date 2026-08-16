package dev.jamal.projetotcc.Repository;

import dev.jamal.projetotcc.Entities.Objective;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ObjectiveRepository
        extends JpaRepository<Objective, Long> {

    Optional<Objective> findByNomeIgnoreCase(String nome);

    boolean existsByNomeIgnoreCase(String nome);
}