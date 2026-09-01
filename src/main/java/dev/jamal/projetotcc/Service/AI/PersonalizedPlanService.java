package dev.jamal.projetotcc.Service.AI;

import dev.jamal.projetotcc.DTO.AI.AIUserContext;
import dev.jamal.projetotcc.Service.AI.provider.AIProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonalizedPlanService {

    private final AIContextService aiContextService;
    private final PromptBuilderService promptBuilderService;
    private final AIProvider aiProvider;


    public String gerarPlano(
      Long userId,
      Long hobbyId
    ) {

        AIUserContext contexto =
                aiContextService.construirContexto(
                        userId,
                        hobbyId
                );

        String prompt =
                promptBuilderService
                        .construirPromptPlanoInicial(
                                contexto
                        );

        return aiProvider.generate(prompt);
    }

}
