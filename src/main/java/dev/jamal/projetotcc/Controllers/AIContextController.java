package dev.jamal.projetotcc.Controllers;

import dev.jamal.projetotcc.DTO.AI.AIUserContext;
import dev.jamal.projetotcc.DTO.AI.PersonalizedPlanResponseDTO;
import dev.jamal.projetotcc.Service.AI.AIContextService;
import dev.jamal.projetotcc.Service.AI.PersonalizedPlanService;
import dev.jamal.projetotcc.Service.AI.PromptBuilderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class AIContextController {

    private final AIContextService aiContextService;
    private final PromptBuilderService promptBuilderService;
    private final PersonalizedPlanService personalizedPlanService;

    @GetMapping("/context/{userId}/{hobbyId}")
    public ResponseEntity<AIUserContext> obterContexto(
            @PathVariable Long userId,
            @PathVariable Long hobbyId
    ) {

        AIUserContext contexto =
                aiContextService.construirContexto(
                        userId,
                        hobbyId
                );

        return ResponseEntity.ok(contexto);
    }

    @GetMapping("/prompt/{userId}/{hobbyId}")
    public ResponseEntity<String> obterPrompt(
            @PathVariable Long userId,
            @PathVariable Long hobbyId
    ) {

        AIUserContext contexto =
                aiContextService.construirContexto(
                        userId,
                        hobbyId
                );

        String prompt =
                promptBuilderService
                        .construirPromptPlanoInicial(contexto);

        return ResponseEntity.ok(prompt);
    }

    @PostMapping("/plan/{userId}/{hobbyId}")
    public ResponseEntity<String> gerarPlano(
        @PathVariable Long userId,
        @PathVariable Long hobbyId
    ) {

        System.out.println(">>> ENTROU NO ENDPOINT DE PLANO");

        String plano =
                personalizedPlanService
                        .gerarPlano(
                                userId,
                                hobbyId
                        );

        return ResponseEntity.ok(plano);
    }

    @GetMapping("/plan/{userId}/{hobbyId}")
    public ResponseEntity<PersonalizedPlanResponseDTO> buscarPlano(
            @PathVariable Long userId,
            @PathVariable Long hobbyId
    ) {
        return ResponseEntity.ok(
                personalizedPlanService.buscarPlano(userId, hobbyId)
        );
    }

    @PostMapping("/plan/{userId}/{hobbyId}/regenerate")
    public ResponseEntity<String> regenerarPlano(
            @PathVariable Long userId,
            @PathVariable Long hobbyId
    ) {
        return ResponseEntity.ok(
                personalizedPlanService.regenerarPlano(
                        userId,
                        hobbyId
                )
        );
    }
}