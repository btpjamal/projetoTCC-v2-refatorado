package dev.jamal.projetotcc.Service.Profile;

import dev.jamal.projetotcc.DTO.Profile.MyProfileHobbyDTO;
import dev.jamal.projetotcc.DTO.Profile.MyProfileResponseDTO;
import dev.jamal.projetotcc.Entities.RecommendationProfile;
import dev.jamal.projetotcc.Entities.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class MyProfileResumeService {

    public String construirResumo(
            User user,
            Integer idade,
            RecommendationProfile profile,
            List<String> interesses,
            List<String> objetivos,
            List<MyProfileHobbyDTO> praticando,
            List<MyProfileHobbyDTO> interessados,
            List<MyProfileResponseDTO.RecommendedHobbyDTO> recomendados
    ) {

        StringBuilder resumo = new StringBuilder();

        resumo.append(user.getNome());

        if (idade != null) {
            resumo.append(", ")
                    .append(idade)
                    .append(" anos");
        }

        if (profile.getCidade() != null && profile.getEstado() != null) {
            resumo.append(", de ")
                    .append(profile.getCidade())
                    .append(" - ")
                    .append(profile.getEstado());
        }

        resumo.append(". ");

        if (profile.getTempoDisponivelSemanal() != null) {
            resumo.append("Tenho até ")
                    .append(formatarNumero(profile.getTempoDisponivelSemanal()))
                    .append(" horas por semana para dedicar aos meus hobbies. ");
        }

        if (profile.getOrcamentoInicial() != null) {
            resumo.append("Pretendo investir inicialmente até R$ ")
                    .append(String.format(
                            Locale.forLanguageTag("pt-BR"),
                            "%.2f",
                            profile.getOrcamentoInicial()
                    ))
                    .append(". ");
        }

        if (!interesses.isEmpty()) {
            resumo.append("Me interesso principalmente por ")
                    .append(formatarLista(interesses))
                    .append(". ");
        }

        if (!objetivos.isEmpty()) {
            resumo.append("Com meus hobbies, busco ")
                    .append(formatarLista(objetivos))
                    .append(". ");
        }

        if (!praticando.isEmpty()) {
            resumo.append("Atualmente estou praticando ")
                    .append(formatarNomesHobbies(praticando))
                    .append(". ");
        } else {
            resumo.append(
                    "Ainda não estou praticando nenhum hobby regularmente. "
            );
        }

        if (!interessados.isEmpty()) {
            resumo.append("Também tenho interesse em ")
                    .append(formatarNomesHobbies(interessados))
                    .append(". ");
        } else {
            resumo.append(
                    "Ainda estou explorando quais atividades gostaria de experimentar. "
            );
        }

        if (!recomendados.isEmpty()) {
            resumo.append(
                            "Pelo meu perfil, acho que poderia me interessar por "
                    )
                    .append(formatarNomesRecomendados(recomendados))
                    .append(".");
        }

        return resumo.toString();
    }

    private String formatarNomesHobbies(
            List<MyProfileHobbyDTO> hobbies
    ) {

        List<String> nomes = hobbies.stream()
                .map(MyProfileHobbyDTO::nome)
                .toList();

        return formatarLista(nomes);
    }

    private String formatarNomesRecomendados(
            List<MyProfileResponseDTO.RecommendedHobbyDTO> hobbies
    ) {

        List<String> nomes = hobbies.stream()
                .map(MyProfileResponseDTO.RecommendedHobbyDTO::nome)
                .toList();

        return formatarLista(nomes);
    }

    private String formatarLista(List<String> itens) {

        if (itens.isEmpty()) {
            return "";
        }

        if (itens.size() == 1) {
            return itens.get(0);
        }

        if (itens.size() == 2) {
            return itens.get(0)
                    + " e "
                    + itens.get(1);
        }

        return String.join(
                ", ",
                itens.subList(0, itens.size() - 1)
        )
                + " e "
                + itens.get(itens.size() - 1);
    }

    private String formatarNumero(Double valor) {
        if (valor % 1 == 0) {
            return String.valueOf(valor.intValue());
        }

        return String.valueOf(valor);
    }
}