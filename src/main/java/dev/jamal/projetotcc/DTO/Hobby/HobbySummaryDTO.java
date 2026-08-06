package dev.jamal.projetotcc.DTO.Hobby;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HobbySummaryDTO {

    private Long id;
    private String nome;
    private String categoria;
}
