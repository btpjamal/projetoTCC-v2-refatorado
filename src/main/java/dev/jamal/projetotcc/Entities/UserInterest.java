package dev.jamal.projetotcc.Entities;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_interest")
public class UserInterest {

    // isso define uma chave composta
    @EmbeddedId
    private UserInterestId id;

    // vários interesses de usuário para um usuário
    @ManyToOne(fetch = FetchType.LAZY) // evita carregamento desnecessário
    // liga o campo da entidade com o campo da chave composta
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // vários interesses de usuário para um interesse
    @ManyToOne(fetch = FetchType.LAZY) // evita carregamento desnecessário
    // liga o campo da entidade com o campo da chave composta
    @MapsId("interestId")
    @JoinColumn(name = "interest_id", nullable = false)
    private Interest interest;

    @Column(nullable = false)
    private Integer peso; // o quanto o usuário gosta daquilo

}
