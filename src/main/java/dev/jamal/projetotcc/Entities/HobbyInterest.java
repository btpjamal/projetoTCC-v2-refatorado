package dev.jamal.projetotcc.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "hobby_interests")
@Getter
@Setter
public class HobbyInterest {

    @EmbeddedId
    private HobbyInterestId id;

    @ManyToOne
    @MapsId("hobbyId")
    @JoinColumn(name = "hobby_id")
    private Hobby hobby;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("interestId")
    @JoinColumn(name = "interest_id")
    private Interest interest;

    private Integer peso = 1;


}
