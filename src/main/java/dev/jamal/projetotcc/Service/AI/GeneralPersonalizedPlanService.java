package dev.jamal.projetotcc.Service.AI;

import dev.jamal.projetotcc.DTO.AI.AIGeneralPlanContext;
import dev.jamal.projetotcc.Entities.GeneralPersonalizedPlan;
import dev.jamal.projetotcc.Entities.User;
import dev.jamal.projetotcc.Exception.BusinessException;
import dev.jamal.projetotcc.Repository.GeneralPersonalizedPlanRepository;
import dev.jamal.projetotcc.Repository.UserRepository;
import dev.jamal.projetotcc.Service.AI.provider.AIProvider;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import dev.jamal.projetotcc.DTO.AI.GeneralPersonalizedPlanResponseDTO;

@Service
public class GeneralPersonalizedPlanService {

    private final GeneralPersonalizedPlanRepository repository;
    private final UserRepository userRepository;
    private final AIGeneralPlanContextService contextService;
    private final AIGeneralPlanContextHashService hashService;
    private final PromptBuilderService promptBuilderService;
    private final AIProvider aiProvider;

    public GeneralPersonalizedPlanService(
            GeneralPersonalizedPlanRepository repository,
            UserRepository userRepository,
            AIGeneralPlanContextService contextService,
            AIGeneralPlanContextHashService hashService,
            PromptBuilderService promptBuilderService,
            AIProvider aiProvider
    ) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.contextService = contextService;
        this.hashService = hashService;
        this.promptBuilderService = promptBuilderService;
        this.aiProvider = aiProvider;
    }

    @Transactional
    public GeneralPersonalizedPlanResponseDTO gerarPlano(Long userId) {

        if (repository.existsByUser_Id(userId)) {
            throw new BusinessException(
                    "O usuário já possui um plano geral."
            );
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new BusinessException("Usuário não encontrado.")
                );

        AIGeneralPlanContext context =
                contextService.construir(userId);

        String prompt =
                promptBuilderService.construirPromptPlanoGeral(context);

        String conteudo =
                aiProvider.generate(prompt);

        String contextHash =
                hashService.calcular(context);

        GeneralPersonalizedPlan plano =
                new GeneralPersonalizedPlan();

        plano.setUser(user);
        plano.setConteudo(conteudo);
        plano.setContextHash(contextHash);

        GeneralPersonalizedPlan salvo =
                repository.save(plano);

        return toResponseDTO(salvo, false);
    }

    public GeneralPersonalizedPlanResponseDTO buscarPlano(Long userId) {

        GeneralPersonalizedPlan plano =
                repository.findByUser_Id(userId)
                        .orElseThrow(() ->
                                new BusinessException(
                                        "Plano geral não encontrado."
                                )
                        );

        AIGeneralPlanContext context =
                contextService.construir(userId);

        String hashAtual =
                hashService.calcular(context);

        boolean stale =
                !plano.getContextHash().equals(hashAtual);

        return toResponseDTO(plano, stale);
    }

    @Transactional
    public GeneralPersonalizedPlanResponseDTO regenerarPlano(Long userId) {

        GeneralPersonalizedPlan plano =
                repository.findByUser_Id(userId)
                        .orElseThrow(() ->
                                new BusinessException(
                                        "Plano geral não encontrado."
                                )
                        );

        AIGeneralPlanContext context =
                contextService.construir(userId);

        String prompt =
                promptBuilderService.construirPromptPlanoGeral(context);

        String conteudo =
                aiProvider.generate(prompt);

        String contextHash =
                hashService.calcular(context);

        plano.setConteudo(conteudo);
        plano.setContextHash(contextHash);

        GeneralPersonalizedPlan salvo =
                repository.save(plano);

        return toResponseDTO(salvo, false);
    }

    private GeneralPersonalizedPlanResponseDTO toResponseDTO(
            GeneralPersonalizedPlan plano,
            boolean stale
    ) {
        return new GeneralPersonalizedPlanResponseDTO(
                plano.getId(),
                plano.getConteudo(),
                plano.getDataCriacao(),
                plano.getDataAtualizacao(),
                stale
        );
    }
}