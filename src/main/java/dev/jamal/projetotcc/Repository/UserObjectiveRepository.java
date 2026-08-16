package dev.jamal.projetotcc.Repository;

import dev.jamal.projetotcc.Entities.UserObjective;
import dev.jamal.projetotcc.Entities.UserObjectiveId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserObjectiveRepository
        extends JpaRepository<UserObjective, UserObjectiveId> {

    List<UserObjective> findByUserId(Long userId);

    void deleteByUserId(Long userId);
}