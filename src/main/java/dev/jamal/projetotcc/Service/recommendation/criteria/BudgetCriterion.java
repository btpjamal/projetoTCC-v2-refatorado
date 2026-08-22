package dev.jamal.projetotcc.Service.recommendation.criteria;

import dev.jamal.projetotcc.Entities.*;
import dev.jamal.projetotcc.Service.recommendation.model.CriterionResult;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BudgetCriterion implements RecommendationCriterion {

    @Override
    public CriterionResult avaliar(
            Hobby h,
            RecommendationProfile p,
            List<UserInterest> i,
            List<UserHobbyFeedback> f
    ) {
            if (h.getCustoEstimado() == null
                    || p.getOrcamentoInicial() == null) {
                return CriterionResult.of(0, null);
            }

            double orcamento = p.getOrcamentoInicial();
            double custo = h.getCustoEstimado();

        if (custo <= orcamento) {
            return CriterionResult.of(
                    15,
                    "Está dentro do seu orçamento inicial"
            );
        }

        if (orcamento <= 0) {
            return CriterionResult.warning(
                    -5,
                    "Este hobby possui um custo inicial incompatível com seu orçamento atual"
            );
        }

        double excesso = (custo - orcamento) / orcamento;

        if (excesso <= 0.25) {
            return CriterionResult.warning(
                    7,
                    "O custo inicial ultrapassa um pouco o orçamento informado"
            );
        }

        if (excesso <= 0.75) {
            return CriterionResult.warning(
                    0,
                    "O investimento inicial pode ser maior do que você pretende gastar"
            );
        }

        return CriterionResult.warning(
                -5,
                "O custo inicial é significativamente maior que o seu orçamento");
    }
}
