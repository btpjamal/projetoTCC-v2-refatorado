package dev.jamal.projetotcc.Service.AI.context;

import dev.jamal.projetotcc.Entities.Hobby;
import dev.jamal.projetotcc.Entities.User;
import dev.jamal.projetotcc.Entities.RecommendationProfile;

// esse objeto agrupa tudo oque o construtor do prompt precisa saber

public record InitialPlanContext(
   User user,
   RecommendationProfile profile,
   Hobby hobby
) {
}
