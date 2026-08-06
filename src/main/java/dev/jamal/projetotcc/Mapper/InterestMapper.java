package dev.jamal.projetotcc.Mapper;

import dev.jamal.projetotcc.DTO.Interest.InterestCreateRequestDTO;
import dev.jamal.projetotcc.DTO.Interest.InterestResponseDTO;
import dev.jamal.projetotcc.Entities.Interest;
import org.springframework.stereotype.Component;

@Component
public class InterestMapper {

    public Interest toEntity(InterestCreateRequestDTO dto){
        Interest interest= new Interest();
        interest.setNome(dto.getNome());
        return interest;
    }

    public InterestResponseDTO toResponseDTO(Interest interest){
        return new InterestResponseDTO(
                interest.getId(),
                interest.getNome()
        );
    }
}
