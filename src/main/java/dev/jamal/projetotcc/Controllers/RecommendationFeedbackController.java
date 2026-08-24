package dev.jamal.projetotcc.Controllers;

import dev.jamal.projetotcc.DTO.Recommendation.RecommendationFeedbackRequestDTO;
import dev.jamal.projetotcc.Service.RecommendationFeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
