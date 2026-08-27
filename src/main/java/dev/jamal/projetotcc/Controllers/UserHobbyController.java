package dev.jamal.projetotcc.Controllers;

import dev.jamal.projetotcc.DTO.UserHobby.UserHobbyLevelRequestDTO;
import dev.jamal.projetotcc.DTO.UserHobby.UserHobbyResponseDTO;
import dev.jamal.projetotcc.Service.UserHobbyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user-hobbies")
@RequiredArgsConstructor
public class UserHobbyController {

    private final UserHobbyService service;

    @PatchMapping("/{userId}/{hobbyId}/nivel")
    public ResponseEntity<UserHobbyResponseDTO> atualizarNivel(
            @PathVariable
            Long userId,
            @PathVariable
            Long hobbyId,
            @Valid @RequestBody
            UserHobbyLevelRequestDTO dto
    ) {

        return ResponseEntity.ok(
                service.atualizarNivel(
                        userId,
                        hobbyId,
                        dto.getNivelAtual()
                )
        );
    }
}
