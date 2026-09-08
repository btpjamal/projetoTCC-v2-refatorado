package dev.jamal.projetotcc.DTO.AI;

import java.time.LocalDateTime;

public record GeneralPersonalizedPlanResponseDTO(
        Long id,
        String conteudo,
        LocalDateTime dataCriacao,
        LocalDateTime dataAtualizacao,
        boolean stale
) {
}