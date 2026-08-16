package dev.jamal.projetotcc.Entities;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class HobbyObjectiveId implements Serializable {

    private Long hobbyId;
    private Long objectiveId;

    public Long getHobbyId() {
        return hobbyId;
    }

    public void setHobbyId(Long hobbyId) {
        this.hobbyId = hobbyId;
    }

    public Long getObjectiveId() {
        return objectiveId;
    }

    public void setObjectiveId(Long objectiveId) {
        this.objectiveId = objectiveId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof HobbyObjectiveId that)) {
            return false;
        }

        return Objects.equals(hobbyId, that.hobbyId)
                && Objects.equals(objectiveId, that.objectiveId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hobbyId, objectiveId);
    }
}