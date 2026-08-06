package dev.jamal.projetotcc.Mapper;

import dev.jamal.projetotcc.DTO.Interest.UserInterestCreateRequestDTO;
import dev.jamal.projetotcc.DTO.Interest.UserInterestResponseDTO;
import dev.jamal.projetotcc.Entities.Interest;
import dev.jamal.projetotcc.Entities.User;
import dev.jamal.projetotcc.Entities.UserInterest;
import dev.jamal.projetotcc.Entities.UserInterestId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserInterestMapper {

    private final InterestMapper interestMapper;

    public UserInterest toEntity(
            UserInterestCreateRequestDTO dto,
            User user,
            Interest interest
    ){
        UserInterest userInterest= new UserInterest();

        UserInterestId id = new UserInterestId();
        id.setUserId(user.getId());
        id.setInterestId(interest.getId());

        userInterest.setId(id);
        userInterest.setUser(user);
        userInterest.setInterest(interest);
        userInterest.setPeso(dto.getPeso());

        return userInterest;
    }

    public UserInterestResponseDTO toResponseDTO(UserInterest userInterest){
        return new UserInterestResponseDTO(
                userInterest.getUser().getId(),
                interestMapper.toResponseDTO(userInterest.getInterest()),
                userInterest.getPeso()
        );
    }
}
