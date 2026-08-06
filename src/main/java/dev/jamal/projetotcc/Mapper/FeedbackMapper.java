package dev.jamal.projetotcc.Mapper;

import dev.jamal.projetotcc.DTO.Feedback.FeedbackCreateRequestDTO;
import dev.jamal.projetotcc.DTO.Feedback.FeedbackResponseDTO;
import dev.jamal.projetotcc.Entities.Hobby;
import dev.jamal.projetotcc.Entities.User;
import dev.jamal.projetotcc.Entities.UserHobbyFeedback;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeedbackMapper {

    private final UserMapper userMapper;
    private final HobbyMapper hobbyMapper;

    public UserHobbyFeedback toEntity(
            FeedbackCreateRequestDTO dto,
            User user,
            Hobby hobby
    ){
        UserHobbyFeedback feedback = new UserHobbyFeedback();
        feedback.setUser(user);
        feedback.setHobby(hobby);
        feedback.setRating(dto.getRating());
        return feedback;
    }

    public FeedbackResponseDTO toResponseDTO(UserHobbyFeedback feedback){
        return new FeedbackResponseDTO(
                feedback.getId(),
                userMapper.toSummaryDTO(feedback.getUser()),
                hobbyMapper.toSummaryDTO(feedback.getHobby()),
                feedback.getRating()
        );
    }
}
