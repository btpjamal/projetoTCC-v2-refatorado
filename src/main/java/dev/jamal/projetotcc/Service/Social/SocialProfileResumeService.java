package dev.jamal.projetotcc.Service.Social;

import dev.jamal.projetotcc.DTO.Social.SocialHobbyDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SocialProfileResumeService {

    public String construirResumo(
            String nome,
            List<String> interesses,
            List<SocialHobbyDTO> praticando,
            List<SocialHobbyDTO> interessados
    ) {

        StringBuilder resumo = new StringBuilder();

        resumo.append(nome);

        if (!interesses.isEmpty()) {

            resumo.append(" se interessa principalmente por ");

            resumo.append(
                    formatarLista(interesses)
            );

            resumo.append(".");
        } else {
            resumo.append(" está explorando novos hobbies e interesses.");
        }


        if (!praticando.isEmpty()) {

            List<String> nomes = praticando.stream()
                    .map(SocialHobbyDTO::nome)
                    .toList();

            resumo.append(" Atualmente pratica ");

            resumo.append(
                    formatarLista(nomes)
            );

            resumo.append(".");
        }


        if (!interessados.isEmpty()) {

            List<String> nomes = interessados.stream()
                    .map(SocialHobbyDTO::nome)
                    .toList();

            resumo.append(" Também demonstrou interesse em ");

            resumo.append(
                    formatarLista(nomes)
            );

            resumo.append(".");
        }

        return resumo.toString();
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
}