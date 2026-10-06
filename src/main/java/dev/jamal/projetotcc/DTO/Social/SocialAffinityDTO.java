package dev.jamal.projetotcc.DTO.Social;

import java.util.List;

public record SocialAffinityDTO(
        int score,
        List<String> interessesEmComum,
        List<String> praticandoEmComum,
        List<String> hobbiesDeInteresseEmComum
) {
}
