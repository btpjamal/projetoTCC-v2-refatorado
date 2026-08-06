package dev.jamal.projetotcc.Service.recommendation.criteria;
import dev.jamal.projetotcc.Entities.*; import dev.jamal.projetotcc.Service.recommendation.model.CriterionResult; import java.util.List;
public interface RecommendationCriterion { CriterionResult avaliar(Hobby hobby, RecommendationProfile perfil, List<UserInterest> interesses, List<UserHobbyFeedback> feedbacks); }
