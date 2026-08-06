package dev.jamal.projetotcc.Mapper;

import dev.jamal.projetotcc.DTO.Category.HobbyCategoryCreateRequestDTO;
import dev.jamal.projetotcc.DTO.Category.HobbyCategoryResponseDTO;
import dev.jamal.projetotcc.Entities.HobbyCategory;
import org.springframework.stereotype.Component;

@Component
public class HobbyCategoryMapper {

    public HobbyCategory toEntity(HobbyCategoryCreateRequestDTO dto){
        HobbyCategory category = new HobbyCategory();
        category.setNome(dto.getNome());
        return category;
    }

    public HobbyCategoryResponseDTO toResponseDTO(HobbyCategory category){
        return new HobbyCategoryResponseDTO(
                category.getId(),
                category.getNome()
        );
    }
}
