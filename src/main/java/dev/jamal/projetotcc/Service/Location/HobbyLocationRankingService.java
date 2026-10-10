
package dev.jamal.projetotcc.Service.Location;

import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;

import java.text.Normalizer;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

@Service
public class HobbyLocationRankingService {

    private static final Map<String, Set<String>> REGRAS_ALTA =
            Map.ofEntries(
                    Map.entry("futebol", Set.of("sport=soccer")),
                    Map.entry("corrida", Set.of("sport=athletics")),
                    Map.entry("ciclismo", Set.of("sport=cycling")),
                    Map.entry("natacao", Set.of(
                            "sport=swimming",
                            "leisure=swimming_pool"
                    )),
                    Map.entry("fotografia", Set.of(
                            "tourism=viewpoint",
                            "tourism=attraction"
                    )),
                    Map.entry("desenho", Set.of("amenity=arts_centre")),
                    Map.entry("pintura", Set.of("amenity=arts_centre")),
                    Map.entry("violao", Set.of("music_school=yes")),
                    Map.entry("xadrez", Set.of("sport=chess")),
                    Map.entry("leitura", Set.of("amenity=library")),
                    Map.entry("escrita", Set.of("amenity=library")),
                    Map.entry("estudo de idiomas", Set.of(
                            "amenity=language_school",
                            "language:teaching=*"
                    )),
                    Map.entry("teatro", Set.of(
                            "amenity=theatre",
                            "theatre:genre=*"
                    )),
                    Map.entry("danca", Set.of(
                            "dance:teaching=yes",
                            "sport=dance"
                    )),
                    Map.entry("voluntariado", Set.of("office=ngo")),
                    Map.entry("clube de jogos", Set.of(
                            "leisure=board_games"
                    )),
                    Map.entry("programacao criativa", Set.of()),
                    Map.entry("edicao de video", Set.of()),
                    Map.entry("robotica basica", Set.of("club=robotics")),
                    Map.entry("criacao de jogos", Set.of()),
                    Map.entry("meditacao", Set.of("leisure=garden")),
                    Map.entry("jardinagem", Set.of("leisure=garden")),
                    Map.entry("culinaria", Set.of("cooking:teaching=yes")),
                    Map.entry("caminhada", Set.of(
                            "leisure=park",
                            "leisure=nature_reserve"
                    ))
            );

    public String classificar(String hobbyNome, JsonNode tags) {
        String hobby = normalizar(hobbyNome);

        Set<String> regras = REGRAS_ALTA.getOrDefault(
                hobby,
                Set.of()
        );

        for (String regra : regras) {
            String[] partes = regra.split("=", 2);

            String chave = partes[0];
            String valorEsperado = partes[1];

            if (!tags.has(chave)) {
                continue;
            }

            String valorEncontrado =
                    tags.path(chave).asText("");

            if (valorEsperado.equals("*")
                    || valorEsperado.equalsIgnoreCase(
                    valorEncontrado
            )) {
                return "ALTA";
            }
        }

        return "COMPLEMENTAR";
    }

    public double calcularDistanciaKm(
            double latitudeOrigem,
            double longitudeOrigem,
            double latitudeDestino,
            double longitudeDestino
    ) {
        final double raioTerraKm = 6371.0;

        double diferencaLatitude = Math.toRadians(
                latitudeDestino - latitudeOrigem
        );

        double diferencaLongitude = Math.toRadians(
                longitudeDestino - longitudeOrigem
        );

        double a =
                Math.pow(Math.sin(diferencaLatitude / 2), 2)
                        + Math.cos(Math.toRadians(latitudeOrigem))
                        * Math.cos(Math.toRadians(latitudeDestino))
                        * Math.pow(Math.sin(diferencaLongitude / 2), 2);

        double c = 2 * Math.atan2(
                Math.sqrt(a),
                Math.sqrt(Math.max(0, 1 - a))
        );

        return Math.round(raioTerraKm * c * 10.0) / 10.0;
    }

    private String normalizar(String texto) {
        if (texto == null) {
            return "";
        }

        return Normalizer.normalize(
                texto.trim().toLowerCase(Locale.ROOT),
                Normalizer.Form.NFD
        ).replaceAll("\\p{M}", "");
    }
}
