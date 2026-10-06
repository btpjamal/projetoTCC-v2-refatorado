package dev.jamal.projetotcc.Repository;

import dev.jamal.projetotcc.Entities.Community;
import dev.jamal.projetotcc.Enum.CommunityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommunityRepository
        extends JpaRepository<Community, Long> {

    List<Community> findByTipo(
            CommunityType tipo
    );

    List<Community> findByTipoAndEstado(
            CommunityType tipo,
            String estado
    );

    Optional<Community> findByHobby_IdAndTipo(
            Long hobbyId,
            CommunityType tipo
    );

    boolean existsByHobby_IdAndTipo(
            Long hobbyId,
            CommunityType tipo
    );

    Optional<Community> findByHobby_IdAndTipoAndEstado(
            Long hobbyId,
            CommunityType tipo,
            String estado
    );

    boolean existsByHobby_IdAndTipoAndEstado(
            Long hobbyId,
            CommunityType tipo,
            String estado
    );
}