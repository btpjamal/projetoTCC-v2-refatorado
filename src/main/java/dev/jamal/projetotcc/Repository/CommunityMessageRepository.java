package dev.jamal.projetotcc.Repository;

import dev.jamal.projetotcc.Entities.CommunityMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityMessageRepository
        extends JpaRepository<CommunityMessage, Long> {

    List<CommunityMessage>
    findByCommunity_IdOrderByDataEnvioAsc(
            Long communityId
    );
}