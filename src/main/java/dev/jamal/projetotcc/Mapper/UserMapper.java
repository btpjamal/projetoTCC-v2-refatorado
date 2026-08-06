package dev.jamal.projetotcc.Mapper;

import dev.jamal.projetotcc.DTO.User.UserCreateRequestDTO;
import dev.jamal.projetotcc.DTO.User.UserResponseDTO;
import dev.jamal.projetotcc.DTO.User.UserSummaryDTO;
import dev.jamal.projetotcc.Entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserCreateRequestDTO dto){
        User user = new User();
        user.setNome(dto.getNome());
        user.setEmail(dto.getEmail());

        user.setDataNascimento(dto.getDataNascimento());
        return user;
    }

    public UserResponseDTO toResponseDTO(User user){
        return new UserResponseDTO(
                user.getId(),
                user.getNome(),
                user.getEmail(),
                user.getDataCadastro(),
                user.getDataNascimento()
        );
    }

    public UserSummaryDTO toSummaryDTO(User user){
        return new UserSummaryDTO(
                user.getId(),
                user.getNome()
        );
    }

}
