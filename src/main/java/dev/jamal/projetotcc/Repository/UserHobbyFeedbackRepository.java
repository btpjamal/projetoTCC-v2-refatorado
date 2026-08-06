package dev.jamal.projetotcc.Repository;

import dev.jamal.projetotcc.Entities.UserHobbyFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserHobbyFeedbackRepository extends JpaRepository<UserHobbyFeedback, Long> {

    Optional<UserHobbyFeedback> findByUserIdAndHobbyId(Long userId, Long hobbyId);

    @Query("""
        SELECT f
        FROM UserHobbyFeedback f
        JOIN FETCH f.hobby h
        JOIN FETCH h.category    
        JOIN FETCH f.user    
        WHERE f.user.id =:userId        
    """)
    List<UserHobbyFeedback> buscarComHobbyEUsuario(@Param("userId") Long userId);

}
