package dev.jamal.projetotcc.DTO.UserHobby;

import dev.jamal.projetotcc.Enum.NivelExperiencia;

public record UserHobbyLevelResponseDTO(
        Long hobbyId,
        String nome,
        NivelExperiencia nivelAtual
){
}
