package dev.jamal.projetotcc.DTO.UserHobby;

import dev.jamal.projetotcc.Enum.UserHobbyStatus;

public record UserHobbyStatusRequestDTO (
    UserHobbyStatus statusAtual
){}
