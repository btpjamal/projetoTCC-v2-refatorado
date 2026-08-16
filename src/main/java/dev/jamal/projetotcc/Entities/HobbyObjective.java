package dev.jamal.projetotcc.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "hobby_objectives")
public class HobbyObjective {

    @EmbeddedId
    private HobbyObjectiveId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("hobbyId")
    @JoinColumn(name = "hobby_id")
    private Hobby hobby;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("objectiveId")
    @JoinColumn(name = "objective_id")
    private Objective objective;

    private Integer peso = 1;

    public HobbyObjectiveId getId() {
        return id;
    }

    public void setId(HobbyObjectiveId id) {
        this.id = id;
    }

    public Hobby getHobby() {
        return hobby;
    }

    public void setHobby(Hobby hobby) {
        this.hobby = hobby;
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