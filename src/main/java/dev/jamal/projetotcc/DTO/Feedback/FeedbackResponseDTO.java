package dev.jamal.projetotcc.DTO.Feedback;

import dev.jamal.projetotcc.DTO.Hobby.HobbySummaryDTO;
import dev.jamal.projetotcc.DTO.User.UserSummaryDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackResponseDTO {

    private Long id;
    private UserSummaryDTO user;
    private HobbySummaryDTO hobby;
    private Integer rating;
}
