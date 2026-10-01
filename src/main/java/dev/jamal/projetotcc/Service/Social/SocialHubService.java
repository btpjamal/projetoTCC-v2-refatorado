package dev.jamal.projetotcc.Service.Social;

import dev.jamal.projetotcc.DTO.Social.SocialHobbyDTO;
import dev.jamal.projetotcc.DTO.Social.SocialProfileDTO;
import dev.jamal.projetotcc.Entities.*;
import dev.jamal.projetotcc.Enum.RecommendationFeedbackType;
import dev.jamal.projetotcc.Enum.UserHobbyStatus;
import dev.jamal.projetotcc.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SocialHubService {

    private final UserRepository userRepository;
    private final SocialProfileRepository socialProfileRepository;
    private final UserInterestRepository userInterestRepository;
    private final UserHobbyRepository userHobbyRepository;
    private final UserRecommendationFeedbackRepository feedbackRepository;
    private final SocialProfileResumeService resumeService;


    public SocialProfileDTO buscarPerfilPublico(Long userId) {

        SocialProfile socialProfile =
                socialProfileRepository
                        .findByUser_Id(userId)
                        .filter(SocialProfile::isEnabled)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Perfil social não disponível"
                                )
                        );

        User user = socialProfile.getUser();

        List<String> interesses =
                userInterestRepository
                        .findByUserIdWithInterest(userId)
                        .stream()
                        .map(userInterest ->
                                userInterest
                                        .getInterest()
                                        .getNome()
                        )
                        .sorted()
                        .toList();


        List<UserHobby> userHobbies =
                userHobbyRepository
                        .findByUser_Id(userId);


        List<SocialHobbyDTO> praticando =
                userHobbies.stream()
                        .filter(userHobby ->
                                userHobby.getStatusAtual()
                                        == UserHobbyStatus.PRATICANDO
                        )
                        .map(userHobby ->
                                new SocialHobbyDTO(
                                        userHobby
                                                .getHobby()
                                                .getId(),

                                        userHobby
                                                .getHobby()
                                                .getNome()
                                )
                        )
                        .toList();


        List<SocialHobbyDTO> interessados =
                feedbackRepository
                        .findByUser_IdAndTipo(
                                userId,
                                RecommendationFeedbackType.INTERESSADO
                        )
                        .stream()

                        .filter(feedback -> {

                            Long hobbyId =
                                    feedback
                                            .getHobby()
                                            .getId();

                            return userHobbies.stream()
                                    .filter(userHobby ->
                                            userHobby
                                                    .getHobby()
                                                    .getId()
                                                    .equals(hobbyId)
                                    )
                                    .findFirst()
                                    .map(userHobby ->
                                            userHobby.getStatusAtual()
                                                    == UserHobbyStatus.INTERESSADO
                                    )
                                    .orElse(true);
                        })

                        .map(feedback ->
                                new SocialHobbyDTO(
                                        feedback
                                                .getHobby()
                                                .getId(),

                                        feedback
                                                .getHobby()
                                                .getNome()
                                )
                        )

                        .toList();


        String resumo =
                resumeService.construirResumo(
                        user.getNome(),
                        interesses,
                        praticando,
                        interessados
                );


        return new SocialProfileDTO(
                user.getId(),
                user.getNome(),
                resumo,
                interesses,
                praticando,
                interessados
        );
    }

    public List<SocialProfileDTO> buscarUsuarios(
            Long requesterId
    ) {

        return socialProfileRepository
                .findByEnabledTrue()
                .stream()

                // não mostrar o próprio usuário no Hub
                .filter(socialProfile ->
                        !socialProfile
                                .getUser()
                                .getId()
                                .equals(requesterId)
                )

                .map(socialProfile ->
                        buscarPerfilPublico(
                                socialProfile
                                        .getUser()
                                        .getId()
                        )
                )

                .toList();
    }
}