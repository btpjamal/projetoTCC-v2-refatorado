package dev.jamal.projetotcc.Service.AI;

import dev.jamal.projetotcc.DTO.AI.AIUserContext;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.List;

@Service
public class AIContextHashService {

    private final ObjectMapper objectMapper;

    public AIContextHashService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String calcular(AIUserContext context) {

        try {

            List<String> interessesOrdenados =
                    context.perfil().interesses() == null
                            ? List.of()
                            : context.perfil().interesses()
                            .stream()
                            .sorted()
                            .toList();

            List<String> objetivosOrdenados =
                    context.perfil().objetivos() == null
                            ? List.of()
                            : context.perfil().objetivos()
                            .stream()
                            .sorted()
                            .toList();

            HashContext contextoRelevante = new HashContext(

                    new UsuarioHash(
                            context.usuario().idade(),
                            context.usuario().cidade(),
                            context.usuario().estado()
                    ),

                    new PerfilHash(
                            context.perfil().tempoDisponivelSemanal(),
                            context.perfil().orcamentoInicial(),
                            context.perfil().tipoSocializacao(),
                            context.perfil().nivelAtividadeFisicaDesejada(),
                            context.perfil().ambientePreferido(),
                            interessesOrdenados,
                            objetivosOrdenados
                    ),

                    new HobbyHash(
                            context.hobby().id(),
                            context.hobby().nome(),
                            context.hobby().descricao(),
                            context.hobby().custoEstimado(),
                            context.hobby().tempoNecessario(),
                            context.hobby().nivelDificuldade(),
                            context.hobby().categoria(),
                            context.hobby().tipoSocializacao(),
                            context.hobby().nivelAtividadeFisica(),
                            context.hobby().ambiente()
                    ),

                    new RelacaoComHobbyHash(
                            context.relacaoComHobby().nivelAtual(),
                            context.relacaoComHobby().statusAtual()
                    )
            );

            String json =
                    objectMapper.writeValueAsString(contextoRelevante);

            MessageDigest digest =
                    MessageDigest.getInstance("SHA-256");

            byte[] hash =
                    digest.digest(
                            json.getBytes(StandardCharsets.UTF_8)
                    );

            return HexFormat.of().formatHex(hash);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Não foi possível calcular a versão do contexto.",
                    e
            );
        }
    }

    private record HashContext(
            UsuarioHash usuario,
            PerfilHash perfil,
            HobbyHash hobby,
            RelacaoComHobbyHash relacaoComHobby
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
            Long id,
            String nome,
            String descricao,
            Double custoEstimado,
            Double tempoNecessario,
            Integer nivelDificuldade,
            String categoria,
            String tipoSocializacao,
            String nivelAtividadeFisica,
            String ambiente
    ) {
    }

    private record RelacaoComHobbyHash(
            String nivelAtual,
            String statusAtual
    ) {
    }
}