/*
import dev.jamal.projetotcc.Enum.TipoSocializacao;
package dev.jamal.projetotcc;

import dev.jamal.projetotcc.Entities.*;
import dev.jamal.projetotcc.Repository.*;
import dev.jamal.projetotcc.Service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Profile("dev")
public class TestRepositoryRunner {
    private final UserRepository userRepository;
    private final HobbyRepository hobbyRepository;
    private final HobbyCategoryRepository categoryRepository;
    private final InterestRepository interestRepository;
    private final UserInterestRepository userInterestRepository;
    private final UserHobbyFeedbackRepository feedbackRepository;

    @Bean
    CommandLineRunner run() {
        return args -> {

            // 🟢 Criar categoria
            HobbyCategory cat = new HobbyCategory();
            cat.setNome("Esporte");
            categoryRepository.save(cat);

            // 🟢 Criar hobby
            Hobby hobby = new Hobby();
            hobby.setNome("Corrida");
            hobby.setCategory(cat);
            hobby.setTipoSocializacao(TipoSocializacao.INDIVIDUAL);
            hobbyRepository.save(hobby);

            // 🟢 Criar usuário
            User user = new User();
            user.setNome("João");
            user.setEmail("joao@email.com");
            user.setSenha("123");
            userRepository.save(user);

            // 🟢 Criar interesse
            Interest interest = new Interest();
            interest.setNome("Esportes");
            interestRepository.save(interest);

            // 🟢 Criar relação UserInterest
            UserInterest ui = new UserInterest();
            UserInterestId id = new UserInterestId();
            id.setUserId(user.getId());
            id.setInterestId(interest.getId());

            ui.setId(id);
            ui.setUser(user);
            ui.setInterest(interest);
            ui.setPeso(5);

            userInterestRepository.save(ui);

            // 🟢 Criar feedback
            UserHobbyFeedback feedback = new UserHobbyFeedback();
            feedback.setUser(user);
            feedback.setHobby(hobby);
            feedback.setRating(5);

            feedbackRepository.save(feedback);

            // 🔍 Teste de leitura
            System.out.println("Usuários:");
            userRepository.findAll()
                    .forEach(u -> System.out.println(u.getNome()));

            System.out.println("Hobbies:");
            hobbyRepository.findAll()
                    .forEach(h -> System.out.println(h.getNome()));

        };
    }
}

 */
