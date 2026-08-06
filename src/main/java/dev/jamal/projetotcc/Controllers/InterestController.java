package dev.jamal.projetotcc.Controllers;

import dev.jamal.projetotcc.DTO.Interest.InterestResponseDTO;
import dev.jamal.projetotcc.Service.InterestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/interests")
@RequiredArgsConstructor
@CrossOrigin("*")
public class InterestController {

    private final InterestService interestService;

    @GetMapping
    public ResponseEntity<List<InterestResponseDTO>> listarTodos() {
        return ResponseEntity.ok(
                interestService.listarTodos()
        );
    }
}
