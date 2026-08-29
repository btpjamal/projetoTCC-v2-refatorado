package dev.jamal.projetotcc.DTO.UserHobby;

import dev.jamal.projetotcc.Enum.NivelExperiencia;
import jakarta.validation.constraints.NotNull;

public class UserHobbyLevelRequestDTO {

    @NotNull
    private NivelExperiencia nivelAtual;

    public NivelExperiencia getNivelAtual() {
        return nivelAtual;
    }

    public void setNivelAtual(NivelExperiencia nivelAtual) {
        this.nivelAtual = nivelAtual;
    }
}
