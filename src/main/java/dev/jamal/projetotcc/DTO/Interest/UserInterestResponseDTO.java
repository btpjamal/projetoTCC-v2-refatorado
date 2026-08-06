package dev.jamal.projetotcc.DTO.Interest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInterestResponseDTO {

    private Long userId;
    private InterestResponseDTO interest;
    private Integer peso;
}
