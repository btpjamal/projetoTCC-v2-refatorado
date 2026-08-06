package dev.jamal.projetotcc.Repository;

import dev.jamal.projetotcc.Entities.UserInterest;
import dev.jamal.projetotcc.Entities.UserInterestId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInterestRepository extends JpaRepository<UserInterest, UserInterestId> {

    @Query("""
    SELECT ui
    FROM UserInterest ui
    JOIN FETCH ui.interest
    WHERE ui.user.id = :userId
""")
    List<UserInterest> findByUserIdWithInterest(@Param("userId") Long userId);
}
