package dev.jamal.projetotcc.Service.AI;

import dev.jamal.projetotcc.DTO.AI.AIGeneralPlanContext;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Comparator;
import java.util.HexFormat;
import java.util.List;

@Service
public class AIGeneralPlanContextHashService {

    private final ObjectMapper objectMapper;

    public AIGeneralPlanContextHashService(
            ObjectMapper objectMapper
    ) {
        this.objectMapper = objectMapper;
    }

    public String calcular(
            AIGeneralPlanContext context
    ) {

        try {

            List<String> interesses =
                    context.perfil().interesses() == null
                            ? List.of()
                            : context.perfil()
                            .interesses()
                            .stream()
                            .sorted()
                            .toList();

            List<String> objetivos =
                    context.perfil().objetivos() == null
                            ? List.of()
                            : context.perfil()
                            .objetivos()
                            .stream()
                            .sorted()
                            .toList();

            List<HobbyHash> hobbies =
                    context.hobbies()
                            .stream()
                            .map(hobby ->
                                    new HobbyHash(
                                            hobby.hobbyId(),
                                            hobby.nome(),
                                            hobby.categoria(),
                                            hobby.tempoNecessario(),
                                            hobby.nivelAtividadeFisica(),
                                            hobby.ambiente(),
                                            hobby.nivelAtual(),
                                            hobby.statusAtual()
                                    )
                            )
                            .sorted(
                                    Comparator.comparing(
                                            HobbyHash::hobbyId
                                    )
                            )
                            .toList();

            HashContext contextoRelevante =
                    new HashContext(

                            new UsuarioHash(
                                    context.usuario().idade(),
                                    context.usuario().cidade(),
                                    context.usuario().estado()
                            ),

                            new PerfilHash(
                                    context.perfil()
                                            .tempoDisponivelSemanal(),
                                    context.perfil()
                                            .orcamentoInicial(),
                                    context.perfil()
                                            .tipoSocializacao(),
                                    context.perfil()
                                            .nivelAtividadeFisicaDesejada(),
                                    context.perfil()
                                            .ambientePreferido(),
                                    interesses,
                                    objetivos
                            ),

                            hobbies
                    );

            String json =
                    objectMapper.writeValueAsString(
                            contextoRelevante
                    );

            MessageDigest digest =
                    MessageDigest.getInstance(
                            "SHA-256"
                    );

            byte[] hash =
                    digest.digest(
                            json.getBytes(
                                    StandardCharsets.UTF_8
                            )
                    );

            return HexFormat
                    .of()
                    .formatHex(hash);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Não foi possível calcular a versão do contexto do plano geral.",
                    e
            );
        }
    }

    private record HashContext(
            UsuarioHash usuario,
            PerfilHash perfil,
            List<HobbyHash> hobbies
    ) {
    }

    private record UsuarioHash(
            Integer idade,
            String cidade,
            String estado
    ) {
    }

    private record PerfilHash(
            Double tempoDisponivelSemanal,
            Double orcamentoInicial,
            String tipoSocializacao,
            String nivelAtividadeFisicaDesejada,
            String ambientePreferido,
            List<String> interesses,
            List<String> objetivos
    ) {
    }

    private record HobbyHash(
            Long hobbyId,
            String nome,
            String categoria,
            Double tempoNecessario,
            String nivelAtividadeFisica,
            String ambiente,
            String nivelAtual,
            String statusAtual
    ) {
    }
}