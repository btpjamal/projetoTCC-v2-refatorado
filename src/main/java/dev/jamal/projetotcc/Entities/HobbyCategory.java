package dev.jamal.projetotcc.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "hobby_category")
public class HobbyCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    // uma categoria pertence a vários hobbies
    // mappedBy -> lado inverso da relação
    // fetch = FetchType.LAZY -> evita carregamento desnecessário
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    @JsonIgnore // evita recursão
    private List<Hobby> hobbies;

}
