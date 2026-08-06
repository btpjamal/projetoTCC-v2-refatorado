package dev.jamal.projetotcc.config;

import dev.jamal.projetotcc.DTO.Feedback.FeedbackCreateRequestDTO;
import dev.jamal.projetotcc.DTO.User.UserCreateRequestDTO;
import dev.jamal.projetotcc.DTO.User.UserResponseDTO;
import dev.jamal.projetotcc.Entities.*;
import dev.jamal.projetotcc.Enum.NivelSocial;
import dev.jamal.projetotcc.Repository.*;
import dev.jamal.projetotcc.Service.FeedbackService;
import dev.jamal.projetotcc.Service.HobbyService;
import dev.jamal.projetotcc.Service.RecommendationService;
import dev.jamal.projetotcc.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;

@Configuration
@Profile("dev")
@RequiredArgsConstructor
public class TestServiceRunner {

    private final UserService userService;
    private final HobbyService hobbyService;
    private final FeedbackService feedbackService;

    private final UserRepository userRepository;
    private final HobbyRepository hobbyRepository;
    private final HobbyCategoryRepository categoryRepository;
    private final InterestRepository interestRepository;

    private final RecommendationService recommendationService;
    private final RecommendationProfileRepository recommendationProfileRepository;
    private final UserInterestRepository userInterestRepository;

    private void imprimirCabecalho() {
        System.out.println("\n======================================");
        System.out.println("     INICIANDO TESTE DE RECOMENDAÇÃO");
        System.out.println("======================================");
    }

    private void imprimirRodape() {
        System.out.println("======================================");
        System.out.println("      TESTE FINALIZADO COM SUCESSO");
        System.out.println("======================================\n");
    }

    private record UsuarioTeste(
            Long userId,
            String nome,
            String email
    ) {}

    private UsuarioTeste criarOuAtualizarUsuarioTeste(
            String nome,
            String email,
            String senha,
            LocalDate dataNascimento,
            Double tempoDisponivel,
            Double orcamento,
            NivelSocial nivelSocial
    ) {

        UserResponseDTO user = userRepository.findByEmail(email)
                .map(u -> new UserResponseDTO(
                        u.getId(),
                        u.getNome(),
                        u.getEmail(),
                        u.getDataCadastro(),
                        u.getDataNascimento()
                ))
                .orElseGet(() -> {

                    UserCreateRequestDTO dto =
                            new UserCreateRequestDTO();

                    dto.setNome(nome);
                    dto.setEmail(email);
                    dto.setSenha(senha);
                    dto.setDataNascimento(dataNascimento);

                    return userService.cadastrar(dto);
                });

        User entidade = userRepository.findById(user.getId())
                .orElseThrow();

        RecommendationProfile profile = recommendationProfileRepository
                .findByUserId(user.getId())
                .orElseGet(RecommendationProfile::new);

        profile.setUser(entidade);
        profile.setTempoDisponivel(tempoDisponivel);
        profile.setOrcamento(orcamento);
        profile.setNivelSocial(nivelSocial);

        recommendationProfileRepository.save(profile);

        System.out.println("Usuário carregado: " + nome);

        return new UsuarioTeste(
                user.getId(),
                user.getNome(),
                user.getEmail()
        );
    }

    private void adicionarOuAtualizarInteresse(
            Long userId,
            String nomeInteresse,
            Integer peso
    ) {

        Interest interest = interestRepository
                .findByNomeIgnoreCase(nomeInteresse)
                .orElseGet(() -> {
                    Interest novo = new Interest();
                    novo.setNome(nomeInteresse);
                    return interestRepository.save(novo);
                });

        User user = userRepository.findById(userId)
                .orElseThrow();

        UserInterestId id = new UserInterestId();
        id.setUserId(userId);
        id.setInterestId(interest.getId());

        UserInterest userInterest =
                userInterestRepository.findById(id)
                        .orElseGet(UserInterest::new);

        userInterest.setId(id);
        userInterest.setUser(user);
        userInterest.setInterest(interest);
        userInterest.setPeso(peso);

        userInterestRepository.save(userInterest);

        System.out.println(
                "Interesse configurado: "
                        + nomeInteresse
                        + " (peso "
                        + peso
                        + ")"
        );
    }

    private void avaliarHobbySeNaoAvaliado(
            Long userId,
            String nomeHobby,
            Integer nota
    ) {

        Hobby hobby = hobbyRepository
                .findByNomeIgnoreCase(nomeHobby)
                .orElseThrow();

        boolean existe = feedbackService
                .listarPorUsuario(userId)
                .stream()
                .anyMatch(f ->
                        f.getHobby()
                                .getId()
                                .equals(hobby.getId())
                );

        if (!existe) {

            FeedbackCreateRequestDTO dto =
                    new FeedbackCreateRequestDTO();

            dto.setHobbyId(hobby.getId());
            dto.setRating(nota);

            feedbackService.avaliar(userId,dto);

            System.out.println(
                    "Feedback criado: "
                            + nomeHobby
                            + " nota "
                            + nota
            );

        } else {
            System.out.println(
                    "Feedback já existente para "
                            + nomeHobby
            );
        }
    }

    private void imprimirPerfilUsuario(Long userId) {

        RecommendationProfile profile = recommendationProfileRepository
                .findByUserId(userId)
                .orElseThrow();

        System.out.println("\n===== PERFIL =====");

        System.out.println("Tempo disponível: "
                + profile.getTempoDisponivel() + "h");

        System.out.println("Orçamento: R$ "
                + profile.getOrcamento());

        System.out.println("Perfil social: "
                + profile.getNivelSocial());
    }

    private void imprimirRecomendacoes(Long userId) {

        System.out.println("\n===== RECOMENDAÇÕES =====");

        recommendationService.recomendar(userId)
                .forEach(r ->
                        System.out.println(
                                "- "
                                        + r.getNome()
                                        + " | Score "
                                        + r.getScore()
                                        + "\n  "
                                        + r.getMotivos()
                        )
                );
    }

    private void imprimirHistorico(Long userId) {
        System.out.println("\n===== HOBBIES JÁ EXPERIMENTADOS =====");

        feedbackService.listarPorUsuario(userId)
                .forEach(f ->
                        System.out.println(
                                "- "
                                + f.getHobby().getNome()
                                + " (nota "
                                + f.getRating()
                                + ")"
                        )
                );
    }

    private void imprimirResumo(Long userId){
        System.out.println("\n===== RESUMO =====");

        System.out.println("Usuários: " +
                userRepository.count());

        System.out.println("Categorias: " +
                categoryRepository.count());

        System.out.println("Hobbies: " +
                hobbyRepository.count());

        System.out.println("Interesses: " +
                interestRepository.count());

        System.out.println("Feedbacks usuário:");

        feedbackService.listarPorUsuario(userId)
                .forEach(f ->
                        System.out.println(
                                f.getHobby().getNome()
                                        + " -> Nota "
                                        + f.getRating()
                        )
                );
    }

    @Bean
    CommandLineRunner run() {
        return args -> {

            imprimirCabecalho();

            System.out.println("\n########## JOÃO ##########");

            UsuarioTeste joao = criarOuAtualizarUsuarioTeste(
                    "João",
                    "joao@email.com",
                    "123",
                    LocalDate.of(2000, 8, 26),
                    2.0,
                    50.0,
                    NivelSocial.INTROVERTIDO
            );

            adicionarOuAtualizarInteresse(joao.userId(), "Esporte", 5);
            adicionarOuAtualizarInteresse(joao.userId(), "Criatividade", 3);

            avaliarHobbySeNaoAvaliado(joao.userId(), "Corrida", 5);
            avaliarHobbySeNaoAvaliado(joao.userId(), "Futebol", 2);

            imprimirPerfilUsuario(joao.userId());
            imprimirRecomendacoes(joao.userId());
            imprimirHistorico(joao.userId());
            imprimirResumo(joao.userId());

            System.out.println("\n########## MARIA ##########");

            UsuarioTeste maria = criarOuAtualizarUsuarioTeste(
                    "Maria",
                    "maria@email.com",
                    "123",
                    LocalDate.of(1998,4,15),
                    4.0,
                    120.0,
                    NivelSocial.EXTROVERTIDO
            );

            adicionarOuAtualizarInteresse(maria.userId(), "Social", 5);
            adicionarOuAtualizarInteresse(maria.userId(), "Criatividade", 4);
            avaliarHobbySeNaoAvaliado(maria.userId(), "Teatro", 5);

            imprimirPerfilUsuario(maria.userId());
            imprimirRecomendacoes(maria.userId());
            imprimirHistorico(maria.userId());
            imprimirResumo(maria.userId());

            imprimirRodape();

        };

    }

}