package dev.jamal.projetotcc.Controllers;

import dev.jamal.projetotcc.DTO.Recommendation.RecommendationFeedbackRequestDTO;
import dev.jamal.projetotcc.DTO.Recommendation.RecommendationFeedbackResponseDTO;
import dev.jamal.projetotcc.Enum.RecommendationFeedbackType;
import dev.jamal.projetotcc.Service.RecommendationFeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recommendation-feedbacks")
@RequiredArgsConstructor
public class RecommendationFeedbackController {

    private final RecommendationFeedbackService service;

    @PostMapping("/{userId}/{hobbyId}")
    public ResponseEntity<Void> registrar(
            @PathVariable Long userId,
            @PathVariable Long hobbyId,
            @RequestBody RecommendationFeedbackRequestDTO dto
    ) {
        service.registrar(
                userId,
                hobbyId,
                dto.getTipo()
        );

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<RecommendationFeedbackResponseDTO>> listar(
            @PathVariable Long userId,
            @RequestParam RecommendationFeedbackType tipo
    ) {

        return ResponseEntity.ok(
                service.listarPorTipo(userId, tipo)
        );
    }

    @DeleteMapping("/{userId}/{hobbyId}")
    public ResponseEntity<Void> removerDecisao(
            @PathVariable Long userId,
            @PathVariable Long hobbyId
    ) {
        service.removerDecisao(userId, hobbyId);

        return ResponseEntity.noContent().build();
    }
}
