package dev.jamal.projetotcc.Entities;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class HobbyInterestId implements Serializable {

    private Long hobbyId;
    private Long interestId;


}
