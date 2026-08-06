package dev.jamal.projetotcc.Service;

import dev.jamal.projetotcc.DTO.Interest.InterestResponseDTO;
import dev.jamal.projetotcc.Entities.Interest;
import dev.jamal.projetotcc.Mapper.InterestMapper;
import dev.jamal.projetotcc.Repository.InterestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InterestService {

    private final InterestRepository interestRepository;
    private final InterestMapper interestMapper;

    @Transactional
    public List<InterestResponseDTO> listarTodos(){
        return interestRepository.findAll()
                .stream()
                .map(interestMapper::toResponseDTO)
                .toList();
    }
}
