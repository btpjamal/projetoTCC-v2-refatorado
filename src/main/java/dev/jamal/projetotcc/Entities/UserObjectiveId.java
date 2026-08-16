package dev.jamal.projetotcc.Entities;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserObjectiveId implements Serializable {

    private Long userId;
    private Long objectiveId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

        if (!(o instanceof UserObjectiveId that)) {
            return false;
        }

        return Objects.equals(userId, that.userId)
                && Objects.equals(objectiveId, that.objectiveId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, objectiveId);
    }
}