package dev.jamal.projetotcc.Controllers;

import dev.jamal.projetotcc.DTO.Social.CommunityDTO;
import dev.jamal.projetotcc.DTO.Social.SocialProfileDTO;
import dev.jamal.projetotcc.Entities.User;
import dev.jamal.projetotcc.Repository.UserRepository;
import dev.jamal.projetotcc.Service.Social.CommunityService;
import dev.jamal.projetotcc.Service.Social.SocialHubService;
import dev.jamal.projetotcc.Service.Social.SocialProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/community")
@RequiredArgsConstructor
public class CommunityController {

    private final SocialHubService socialHubService;
    private final SocialProfileService socialProfileService;
    private final UserRepository userRepository;
    private final CommunityService communityService;


    @GetMapping("/users")
    public ResponseEntity<List<SocialProfileDTO>> usuarios(
            Authentication authentication
    ) {

        Long userId = obterUserId(authentication);

        validarAcessoSocial(userId);

        return ResponseEntity.ok(
                socialHubService.buscarUsuarios(userId)
        );
    }


    @GetMapping("/users/{userId}")
    public ResponseEntity<SocialProfileDTO> usuario(
            @PathVariable Long userId,
            Authentication authentication
    ) {

        Long requesterId =
                obterUserId(authentication);

        validarAcessoSocial(requesterId);

        return ResponseEntity.ok(
                socialHubService.buscarPerfilPublico(userId)
        );
    }

    @GetMapping("/communities")
    public ResponseEntity<List<CommunityDTO>> comunidades(
            Authentication authentication
    ) {

        Long userId = obterUserId(authentication);

        validarAcessoSocial(userId);

        communityService.garantirComunidadesGerais();

        return ResponseEntity.ok(
                communityService.listarGerais(userId)
        );
    }

    @PostMapping("/communities/{communityId}/join")
    public ResponseEntity<CommunityDTO> entrarComunidade(
            @PathVariable Long communityId,
            Authentication authentication
    ) {

        Long userId = obterUserId(authentication);

        validarAcessoSocial(userId);

        return ResponseEntity.ok(
                communityService.entrar(
                        communityId,
                        userId
                )
        );
    }

    @DeleteMapping("/communities/{communityId}/leave")
    public ResponseEntity<CommunityDTO> sairComunidade(
            @PathVariable Long communityId,
            Authentication authentication
    ) {

        Long userId = obterUserId(authentication);

        validarAcessoSocial(userId);

        return ResponseEntity.ok(
                communityService.sair(
                        communityId,
                        userId
                )
        );
    }


    private void validarAcessoSocial(Long userId) {

        if (!socialProfileService.elegivel(userId)) {
            throw new IllegalStateException(
                    "A comunidade está disponível apenas para usuários maiores de 18 anos."
            );
        }

        if (!socialProfileService.participa(userId)) {
            throw new IllegalStateException(
                    "Ative sua participação na comunidade para acessar o Hub Social."
            );
        }
    }


    private Long obterUserId(
            Authentication authentication
    ) {

        String email = authentication.getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Usuário autenticado não encontrado"
                        )
                );

        return user.getId();
    }


    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleIllegalState(
            IllegalStateException exception
    ) {

        return ResponseEntity
                .status(403)
                .body(
                        java.util.Map.of(
                                "error",
                                exception.getMessage()
                        )
                );
    }

    @GetMapping("/communities/regional")
    public ResponseEntity<List<CommunityDTO>> comunidadesRegionais(
            Authentication authentication
    ) {

        Long userId =
                obterUserId(authentication);

        validarAcessoSocial(userId);


        communityService
                .garantirComunidadesRegionais(
                        userId
                );


        return ResponseEntity.ok(
                communityService
                        .listarRegionais(userId)
        );
    }
}