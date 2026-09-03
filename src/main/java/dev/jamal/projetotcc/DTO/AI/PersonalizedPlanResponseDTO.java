package dev.jamal.projetotcc.DTO.AI;

import java.time.LocalDateTime;

public record PersonalizedPlanResponseDTO(
        Long id,
        Long hobbyId,
        String hobbyNome,
        String conteudo,
        LocalDateTime dataCriacao,
        LocalDateTime dataAtualizacao,
        boolean stale
) {
}
