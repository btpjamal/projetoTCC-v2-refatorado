package dev.jamal.projetotcc.Repository;

import dev.jamal.projetotcc.Entities.Interest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InterestRepository extends JpaRepository<Interest, Long> {
    Optional<Interest> findByNomeIgnoreCase(String nome);
}
