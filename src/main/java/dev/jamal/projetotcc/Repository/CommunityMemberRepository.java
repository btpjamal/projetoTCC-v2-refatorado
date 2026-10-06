package dev.jamal.projetotcc.Repository;

import dev.jamal.projetotcc.Entities.CommunityMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommunityMemberRepository
        extends JpaRepository<CommunityMember, Long> {

    boolean existsByCommunity_IdAndUser_Id(
            Long communityId,
            Long userId
    );

    Optional<CommunityMember>
    findByCommunity_IdAndUser_Id(
            Long communityId,
            Long userId
    );

    List<CommunityMember> findByUser_Id(
            Long userId
    );

    long countByCommunity_Id(
            Long communityId
    );
}