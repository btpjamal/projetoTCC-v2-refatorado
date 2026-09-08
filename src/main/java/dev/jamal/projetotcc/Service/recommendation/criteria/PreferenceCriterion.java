package dev.jamal.projetotcc.Service.recommendation.criteria;

import dev.jamal.projetotcc.Enum.TipoSocializacao;
import dev.jamal.projetotcc.Entities.*;
import dev.jamal.projetotcc.Enum.*;
import dev.jamal.projetotcc.Service.recommendation.model.CriterionResult;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PreferenceCriterion implements RecommendationCriterion {

    @Override
    public CriterionResult avaliar(
            Hobby h,
            RecommendationProfile p,
            List<UserInterest> i,
            List<UserHobbyFeedback> f
    ) {

        double score = 0;
        List<String> motivos = new ArrayList<>();
        List<String> alertas = new ArrayList<>();

        if (p.getTipoSocializacao() != null
            && h.getTipoSocializacao() != null) {

            if (p.getTipoSocializacao() == TipoSocializacao.INDIFERENTE
            || p.getTipoSocializacao() == h.getTipoSocializacao()) {
                score += 7;
                motivos.add(
                        "Combina com sua preferência de socialização"
                );
            }
        }

        if (p.getAmbientePreferido() != null
            && h.getAmbiente() != null) {

            if (p.getAmbientePreferido() == AmbientePreferido.INDIFERENTE
                    || p.getAmbientePreferido() == h.getAmbiente()) {

                score += 5;
                motivos.add(
                        "Pode ser praticado no ambiente que você prefere"
                );
            } else {
                alertas.add(
                        "O ambiente mais comum deste hobby difere da sua preferência"
                );
            }
        }

        if (p.getNivelAtividadeFisicaDesejada() != null && h.getNivelAtividadeFisica() != null) {
            if (p.getNivelAtividadeFisicaDesejada() == NivelAtividadeFisica.INDIFERENTE
                || p.getNivelAtividadeFisicaDesejada() == h.getNivelAtividadeFisica()) {
                score += 4;
                motivos.add(
                        "Tem o nível de atividade física que você procura"
                );
            }
        }

        return new CriterionResult(
                score,
                motivos,
                alertas
        );

    }
}
