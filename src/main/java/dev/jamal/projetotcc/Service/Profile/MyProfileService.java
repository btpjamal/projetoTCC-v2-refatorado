package dev.jamal.projetotcc.Service.Profile;

import dev.jamal.projetotcc.DTO.Profile.MyProfileHobbyDTO;
import dev.jamal.projetotcc.DTO.Profile.MyProfileResponseDTO;
import dev.jamal.projetotcc.DTO.Recommendation.RecommendationFeedbackRequestDTO;
import dev.jamal.projetotcc.Entities.*;
import dev.jamal.projetotcc.Enum.RecommendationFeedbackType;
import dev.jamal.projetotcc.Enum.UserHobbyStatus;
import dev.jamal.projetotcc.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyProfileService {

    private final UserRepository userRepository;
    private final RecommendationProfileRepository profileRepository;
    private final UserInterestRepository userInterestRepository;
    private final UserObjectiveRepository userObjectiveRepository;
    private final UserHobbyRepository userHobbyRepository;
    private final UserRecommendationFeedbackRepository feedbackRepository;

    public MyProfileResponseDTO buscarPerfil(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado")
                );

        RecommendationProfile profile = profileRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new RuntimeException("Perfil de recomendação não encontrado")
                );

        List<String> interesses = userInterestRepository
                .findByUserIdWithInterest(userId)
                .stream()
                .map(userInterest -> userInterest.getInterest().getNome())
                .sorted()
                .toList();

        List<String> objetivos = userObjectiveRepository
                .findByUserId(userId)
                .stream()
                .map(userObjective -> userObjective.getObjective().getNome())
                .sorted()
                .toList();

        Integer idade = calcularIdade(user.getDataNascimento());

        List<UserHobby> userHobbies =
                userHobbyRepository.findByUser_Id(userId);

        List<MyProfileHobbyDTO> praticando = userHobbies.stream()
                .filter(userHobby ->
                        userHobby.getStatusAtual() == UserHobbyStatus.PRATICANDO
                )
                .map(this::toHobbyDTO)
                .toList();

        List<UserRecommendationFeedback> feedbacksInteressados =
                feedbackRepository
                        .findByUser_IdAndTipo(
                                userId,
                                RecommendationFeedbackType.INTERESSADO
                        );
        List<MyProfileHobbyDTO> interessados =
                feedbacksInteressados.stream()
                        .map(feedback -> {

                            Hobby hobby = feedback.getHobby();

                            UserHobby userHobby = userHobbies.stream()
                                    .filter(uh ->
                                            uh.getHobby().getId().equals(hobby.getId())
                                    )
                                    .findFirst()
                                    .orElse(null);

                            return new MyProfileHobbyDTO(
                                    hobby.getId(),
                                    hobby.getNome(),

                                    userHobby != null && userHobby.getNivelAtual() != null
                                            ? userHobby.getNivelAtual().name()
                                            : null,

                                    userHobby != null && userHobby.getStatusAtual() != null
                                            ? userHobby.getStatusAtual().name()
                                            : "INTERESSADO"
                            );
                        })
                        .toList();

        return new MyProfileResponseDTO(
                user.getNome(),
                idade,
                profile.getCidade(),
                profile.getEstado(),

                null,           // resumo

                praticando,      // praticando
                interessados,      // interessados
                List.of(),      // recomendados

                profile.getTempoDisponivelSemanal(),
                profile.getOrcamentoInicial(),

                profile.getTipoSocializacao() != null
                        ? profile.getTipoSocializacao().name()
                        : null,

                profile.getNivelAtividadeFisicaDesejada() != null
                        ? profile.getNivelAtividadeFisicaDesejada().name()
                        : null,

                profile.getAmbientePreferido() != null
                        ? profile.getAmbientePreferido().name()
                        : null,

                interesses,
                objetivos
        );
    }

    private Integer calcularIdade(LocalDate dataNascimento) {

        if (dataNascimento == null) {
            return null;
        }

        return Period.between(
                dataNascimento,
                LocalDate.now()
        ).getYears();
    }

    private MyProfileHobbyDTO toHobbyDTO(UserHobby userHobby) {

        return new MyProfileHobbyDTO(
                userHobby.getHobby().getId(),
                userHobby.getHobby().getNome(),

                userHobby.getNivelAtual() != null
                        ? userHobby.getNivelAtual().name()
                        : null,

                userHobby.getStatusAtual() != null
                        ? userHobby.getStatusAtual().name()
                        : null
        );
    }
}
