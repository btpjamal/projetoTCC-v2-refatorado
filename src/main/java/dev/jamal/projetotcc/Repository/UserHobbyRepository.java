package dev.jamal.projetotcc.Repository;

import dev.jamal.projetotcc.Entities.UserHobby;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserHobbyRepository
        extends JpaRepository<UserHobby, Long> {

    Optional<UserHobby> findByUser_IdAndHobby_Id(
            Long userId,
            Long hobbyId
    );

    List<UserHobby> findByUser_Id(Long userId);
}
