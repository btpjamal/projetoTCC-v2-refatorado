package dev.jamal.projetotcc.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity

// essa parte evita que o mesmo usuário possa avaliar o mesmo hobby duas vezes
@Table(name = "user_hobby_feedback",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "hobby_id"})
})
public class UserHobbyFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Min(1)
    @Max(5)
    private Integer rating; // nota de 1 a 5

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hobby_id", nullable = false)
    private Hobby hobby;


}
