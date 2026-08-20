package dev.jamal.projetotcc.Controllers;

import dev.jamal.projetotcc.Entities.Objective;
import dev.jamal.projetotcc.Repository.ObjectiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/objectives")
@RequiredArgsConstructor
public class ObjectiveController {

    private final ObjectiveRepository objectiveRepository;

    @GetMapping
    public List<Objective> listar(){
        return objectiveRepository.findAll();
    }
}
