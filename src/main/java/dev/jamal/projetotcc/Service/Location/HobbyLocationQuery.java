
package dev.jamal.projetotcc.Service.Location;

import java.text.Normalizer;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import static java.util.Map.entry;

public final class HobbyLocationQuery {

    private HobbyLocationQuery() {}

    private static final Map<String, String> FILTROS = Map.ofEntries(

            // ESPORTES
            entry("futebol", """
            nwr["sport"="soccer"](around:12000,%s,%s);
            nwr["leisure"="pitch"]["sport"="multi"](around:12000,%s,%s);
            """),

            entry("corrida", """
            nwr["leisure"="park"](around:12000,%s,%s);
            nwr["sport"="athletics"](around:12000,%s,%s);
            """),

            entry("ciclismo", """
            nwr["sport"="cycling"](around:12000,%s,%s);
            nwr["leisure"="park"](around:12000,%s,%s);
            """),

            entry("natacao", """
            nwr["sport"="swimming"](around:12000,%s,%s);
            nwr["leisure"="swimming_pool"](around:12000,%s,%s);
            """),

            // CRIATIVIDADE
            entry("fotografia", """
            nwr["tourism"="viewpoint"](around:12000,%s,%s);
            nwr["tourism"="attraction"](around:12000,%s,%s);
            """),

            entry("desenho", """
            nwr["shop"="art"](around:12000,%s,%s);
            nwr["amenity"="arts_centre"](around:12000,%s,%s);
            """),

            entry("pintura", """
            nwr["shop"="art"](around:12000,%s,%s);
            nwr["amenity"="arts_centre"](around:12000,%s,%s);
            """),

            entry("violao", """
            nwr["shop"="musical_instrument"](around:12000,%s,%s);
            nwr["music_school"="yes"](around:12000,%s,%s);
            """),

            // INTELECTUAL
            entry("xadrez", """
            nwr["sport"="chess"](around:12000,%s,%s);
            nwr["amenity"="library"](around:12000,%s,%s);
            """),

            entry("leitura", """
            nwr["amenity"="library"](around:12000,%s,%s);
            nwr["shop"="books"](around:12000,%s,%s);
            """),

            entry("escrita", """
            nwr["amenity"="library"](around:12000,%s,%s);
            nwr["amenity"="arts_centre"](around:12000,%s,%s);
            """),

            entry("estudo de idiomas", """
            nwr["language:teaching"](around:12000,%s,%s);
            nwr["amenity"="language_school"](around:12000,%s,%s);
            """),

            // SOCIAL
            entry("teatro", """
            nwr["amenity"="theatre"](around:12000,%s,%s);
            nwr["theatre:genre"](around:12000,%s,%s);
            """),

            entry("danca", """
            nwr["dance:teaching"="yes"](around:12000,%s,%s);
            nwr["sport"="dance"](around:12000,%s,%s);
            """),

            entry("voluntariado", """
            nwr["office"="ngo"](around:12000,%s,%s);
            nwr["office"="association"](around:12000,%s,%s);
            """),

            entry("clube de jogos", """
            nwr["leisure"="board_games"](around:12000,%s,%s);
            nwr["shop"="games"](around:12000,%s,%s);
            """),

            // TECNOLOGIA - LOCAIS COMPLEMENTARES
            entry("programacao criativa", """
            nwr["amenity"="library"](around:12000,%s,%s);
            nwr["office"="coworking"](around:12000,%s,%s);
            """),

            entry("edicao de video", """
            nwr["office"="coworking"](around:12000,%s,%s);
            nwr["amenity"="arts_centre"](around:12000,%s,%s);
            """),

            entry("robotica basica", """
            nwr["club"="robotics"](around:12000,%s,%s);
            nwr["craft"="electronics"](around:12000,%s,%s);
            """),

            entry("criacao de jogos", """
            nwr["office"="coworking"](around:12000,%s,%s);
            nwr["amenity"="library"](around:12000,%s,%s);
            """),

            // RELAXAMENTO
            entry("meditacao", """
            nwr["leisure"="park"](around:12000,%s,%s);
            nwr["leisure"="garden"](around:12000,%s,%s);
            """),

            entry("jardinagem", """
            nwr["shop"="garden_centre"](around:12000,%s,%s);
            nwr["leisure"="garden"](around:12000,%s,%s);
            """),

            entry("culinaria", """
            nwr["cooking:teaching"="yes"](around:12000,%s,%s);
            nwr["shop"="kitchen"](around:12000,%s,%s);
            """),

            entry("caminhada", """
            nwr["leisure"="park"](around:12000,%s,%s);
            nwr["leisure"="nature_reserve"](around:12000,%s,%s);
            """
            )
    );

    public static Optional<String> construir(
            String hobbyNome,
            double latitude,
            double longitude
    ) {
        if (hobbyNome == null || hobbyNome.isBlank()) {
            return Optional.empty();
        }

        if (!Double.isFinite(latitude)
                || !Double.isFinite(longitude)
                || latitude < -90 || latitude > 90
                || longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException(
                    "Coordenadas inválidas."
            );
        }

        String nome = Normalizer.normalize(
                hobbyNome.trim().toLowerCase(Locale.ROOT),
                Normalizer.Form.NFD
        ).replaceAll("\\p{M}", "");

        String filtros = FILTROS.get(nome);

        if (filtros == null) {
            return Optional.empty();
        }

        String coordenadas = "(around:12000,"
                + latitude + "," + longitude + ")";

        String consulta = filtros.replace(
                "(around:12000,%s,%s)",
                coordenadas
        );

        return Optional.of("""
                [out:json][timeout:25];
                (
                %s
                );
                out center;
                """.formatted(consulta));
    }
}
