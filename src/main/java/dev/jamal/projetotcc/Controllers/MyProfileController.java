package dev.jamal.projetotcc.Controllers;

import dev.jamal.projetotcc.DTO.Profile.MyProfileResponseDTO;
import dev.jamal.projetotcc.Entities.User;
import dev.jamal.projetotcc.Service.Profile.MyProfileService;
import dev.jamal.projetotcc.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class MyProfileController {

    private final MyProfileService myProfileService;
    private final UserService userService;

    @GetMapping("/me")
    public MyProfileResponseDTO buscarMeuPerfil(
            @AuthenticationPrincipal User user
    ) {

        Long userId = userService
                .buscarPorId(user.getId())
                .getId();

        return myProfileService.buscarPerfil(userId);
    }
}
