package dev.jamal.projetotcc.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "user_objectives")
public class UserObjective {

    @EmbeddedId
    private UserObjectiveId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("objectiveId")
    @JoinColumn(name = "objective_id")
    private Objective objective;

    private Integer peso = 1;

    public UserObjectiveId getId() {
        return id;
    }

    public void setId(UserObjectiveId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Objective getObjective() {
        return objective;
    }

    public void setObjective(Objective objective) {
        this.objective = objective;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }
}