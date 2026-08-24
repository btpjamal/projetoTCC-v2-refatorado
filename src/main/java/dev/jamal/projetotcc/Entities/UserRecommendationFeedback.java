package dev.jamal.projetotcc.Entities;

import dev.jamal.projetotcc.Enum.RecommendationFeedbackType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_recommendation_feedbacks")
@Getter
@Setter
public class UserRecommendationFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hobby_id", nullable = false)
    private Hobby hobby;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecommendationFeedbackType tipo;

    private LocalDateTime createdAt = LocalDateTime.now();
}
