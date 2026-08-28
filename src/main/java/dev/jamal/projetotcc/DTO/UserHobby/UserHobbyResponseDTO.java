package dev.jamal.projetotcc.DTO.UserHobby;

import dev.jamal.projetotcc.Enum.NivelExperiencia;
import dev.jamal.projetotcc.Enum.UserHobbyStatus;

public record UserHobbyResponseDTO(
        Long hobbyId,
        String nome,
        NivelExperiencia nivelAtual,
        UserHobbyStatus statusAtual
){
}
