package dev.jamal.projetotcc.Service.AI;

import dev.jamal.projetotcc.DTO.AI.AIUserContext;
import dev.jamal.projetotcc.DTO.AI.PersonalizedPlanResponseDTO;
import dev.jamal.projetotcc.Entities.Hobby;
import dev.jamal.projetotcc.Entities.PersonalizedPlan;
import dev.jamal.projetotcc.Entities.User;
import dev.jamal.projetotcc.Exception.BusinessException;
import dev.jamal.projetotcc.Repository.HobbyRepository;
import dev.jamal.projetotcc.Repository.PersonalizedPlanRepository;
import dev.jamal.projetotcc.Repository.UserRepository;
import dev.jamal.projetotcc.Service.AI.provider.AIProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PersonalizedPlanService {

    private final AIContextService aiContextService;
    private final PromptBuilderService promptBuilderService;
    private final AIProvider aiProvider;

    private final PersonalizedPlanRepository personalizedPlanRepository;
    private final UserRepository userRepository;
    private final HobbyRepository hobbyRepository;
    private final AIContextHashService aiContextHashService;

    @Transactional
    public String gerarPlano(Long userId, Long hobbyId) {

        if (personalizedPlanRepository
                .existsByUser_IdAndHobby_Id(userId, hobbyId)) {

            throw new BusinessException(
                    "Já existe um plano personalizado para este hobby."
            );
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado.")
                );

        Hobby hobby = hobbyRepository.findById(hobbyId)
                .orElseThrow(() ->
                        new RuntimeException("Hobby não encontrado.")
                );

        AIUserContext contexto =
                aiContextService.construirContexto(
                        userId,
                        hobbyId
                );

        String contextHash =
                aiContextHashService.calcular(contexto);

        String prompt =
                promptBuilderService
                        .construirPromptPlanoInicial(contexto);

        String conteudo =
                aiProvider.generate(prompt);

        PersonalizedPlan plano =
                personalizedPlanRepository
                        .findByUser_IdAndHobby_Id(
                                userId,
                                hobbyId
                        )
                        .orElseGet(PersonalizedPlan::new);

        plano.setUser(user);
        plano.setHobby(hobby);
        plano.setConteudo(conteudo);
        plano.setContextHash(contextHash);

        personalizedPlanRepository.save(plano);

        return conteudo;
    }

    @Transactional(readOnly = true)
    public PersonalizedPlanResponseDTO buscarPlano(Long userId, Long hobbyId) {

        PersonalizedPlan plano =
                personalizedPlanRepository
                        .findByUser_IdAndHobby_Id(
                                userId,
                                hobbyId
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Plano personalizado ainda não foi gerado."
                                )
                        );

        AIUserContext contextoAtual =
                aiContextService.construirContexto(
                        userId,
                        hobbyId
                );

        String contextHashAtual =
                aiContextHashService.calcular(contextoAtual);

        boolean stale =
                !plano.getContextHash()
                        .equals(contextHashAtual);

        return new PersonalizedPlanResponseDTO(
                plano.getId(),
                plano.getHobby().getId(),
                plano.getHobby().getNome(),
                plano.getConteudo(),
                plano.getDataCriacao(),
                plano.getDataAtualizacao(),
                stale
        );
    }

    @Transactional
    public String regenerarPlano(
            Long userId,
            Long hobbyId
    ) {

        PersonalizedPlan plano =
                personalizedPlanRepository
                        .findByUser_IdAndHobby_Id(userId, hobbyId)
                        .orElseThrow(() ->
                                new BusinessException(
                                        "Não existe plano personalizado para atualizar."
                                )
                        );

        AIUserContext contexto =
                aiContextService.construirContexto(
                        userId,
                        hobbyId
                );

        String prompt =
                promptBuilderService
                        .construirPromptPlanoInicial(contexto);

        String conteudo =
                aiProvider.generate(prompt);

        String contextHash =
                aiContextHashService.calcular(contexto);

        plano.setConteudo(conteudo);
        plano.setContextHash(contextHash);

        personalizedPlanRepository.save(plano);

        return conteudo;
    }
}