package dev.jamal.projetotcc.Service.Profile;

import dev.jamal.projetotcc.DTO.Profile.MyProfileHobbyDTO;
import dev.jamal.projetotcc.DTO.Profile.MyProfileResponseDTO;
import dev.jamal.projetotcc.DTO.Recommendation.HobbyRecommendationDTO;
import dev.jamal.projetotcc.DTO.Recommendation.RecommendationFeedbackRequestDTO;
import dev.jamal.projetotcc.Entities.*;
import dev.jamal.projetotcc.Enum.RecommendationFeedbackType;
import dev.jamal.projetotcc.Enum.UserHobbyStatus;
import dev.jamal.projetotcc.Repository.*;
import dev.jamal.projetotcc.Service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MyProfileService {

    private final UserRepository userRepository;
    private final RecommendationProfileRepository profileRepository;
    private final UserInterestRepository userInterestRepository;
    private final UserObjectiveRepository userObjectiveRepository;
    private final UserHobbyRepository userHobbyRepository;
    private final UserRecommendationFeedbackRepository feedbackRepository;
    private final RecommendationService recommendationService;
    private final MyProfileResumeService profileResumeService;

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

        List<MyProfileHobbyDTO> praticandoCompleto = userHobbies.stream()
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
        List<MyProfileHobbyDTO> interessadosCompleto =
                feedbacksInteressados.stream()
                        .filter(feedback -> {

                            Long hobbyId = feedback.getHobby().getId();

                            return userHobbies.stream()
                                    .filter(userHobby ->
                                            userHobby.getHobby()
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
                        .map(feedback -> {

                            Hobby hobby = feedback.getHobby();

                            UserHobby userHobby = userHobbies.stream()
                                    .filter(uh ->
                                            uh.getHobby()
                                                    .getId()
                                                    .equals(hobby.getId())
                                    )
                                    .findFirst()
                                    .orElse(null);

                            return new MyProfileHobbyDTO(
                                    hobby.getId(),
                                    hobby.getNome(),

                                    userHobby != null
                                            && userHobby.getNivelAtual() != null
                                            ? userHobby.getNivelAtual().name()
                                            : null,

                                    userHobby != null
                                            && userHobby.getStatusAtual() != null
                                            ? userHobby.getStatusAtual().name()
                                            : "INTERESSADO"
                            );
                        })
                        .toList();

        var recomendacoes =
                recommendationService.recomendar(userId);

        Set<Long> hobbiesJaRelacionados = new HashSet<>();

        praticandoCompleto.forEach(
                hobby -> hobbiesJaRelacionados.add(hobby.hobbyId())
        );

        interessadosCompleto.forEach(
                hobby -> hobbiesJaRelacionados.add(hobby.hobbyId())
        );

        List<MyProfileResponseDTO.RecommendedHobbyDTO> top5 =
                recomendacoes.stream()

                        .filter(recomendacao ->
                                !hobbiesJaRelacionados.contains(
                                        recomendacao.getHobbyId()
                                )
                        )

                        .sorted(
                                Comparator.comparingDouble(
                                        HobbyRecommendationDTO::getScore
                                ).reversed()
                        )

                        .limit(5)

                        .map(recomendacao ->
                                new MyProfileResponseDTO.RecommendedHobbyDTO(
                                        recomendacao.getHobbyId(),
                                        recomendacao.getNome(),
                                        recomendacao.getScore()
                                )
                        )

                        .toList();


        List<MyProfileHobbyDTO> praticando =
                selecionarAleatorios(praticandoCompleto, 3);

        List<MyProfileHobbyDTO> interessados =
                selecionarAleatorios(interessadosCompleto, 3);

        List<MyProfileResponseDTO.RecommendedHobbyDTO> recomendados =
                selecionarAleatorios(top5, 2);

        String resumo = profileResumeService.construirResumo(
                user,
                idade,
                profile,
                interesses,
                objetivos,
                praticando,
                interessados,
                recomendados
        );


        return new MyProfileResponseDTO(
                user.getNome(),
                idade,
                profile.getCidade(),
                profile.getEstado(),

                resumo,           // resumo

                praticando,      // praticando
                interessados,      // interessados
                recomendados,      // recomendados

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

    private <T> List<T> selecionarAleatorios(
            List<T> itens,
            int quantidade
    ) {

        if (itens.size() <= quantidade) {
            return itens;
        }

        List<T> copia = new ArrayList<>(itens);

        Collections.shuffle(copia);

        return copia.stream()
                .limit(quantidade)
                .toList();
    }


}
