package dev.jamal.projetotcc.Repository;

import dev.jamal.projetotcc.Entities.Hobby;
import dev.jamal.projetotcc.Entities.HobbyCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HobbyCategoryRepository extends JpaRepository<HobbyCategory, Long> {

    Optional<HobbyCategory> findByNomeIgnoreCase(String nome);
    boolean existsByNomeIgnoreCase(String nome);
}
