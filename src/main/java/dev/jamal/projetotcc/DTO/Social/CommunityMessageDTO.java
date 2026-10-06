package dev.jamal.projetotcc.DTO.Social;

import java.time.LocalDateTime;

public record CommunityMessageDTO(
        Long id,
        Long userId,
        String autorNome,
        String conteudo,
        LocalDateTime dataEnvio,
        boolean propria
) {
}
