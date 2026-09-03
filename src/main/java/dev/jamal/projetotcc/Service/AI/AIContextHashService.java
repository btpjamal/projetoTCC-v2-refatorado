package dev.jamal.projetotcc.Service.AI;

import dev.jamal.projetotcc.DTO.AI.AIUserContext;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.Map;

@Service
public class AIContextHashService {

    private final ObjectMapper objectMapper;

    public AIContextHashService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String calcular(AIUserContext context) {

        try {

            Map<String, Object> contextoRelevante = Map.of(
                    "usuario", context.usuario(),
                    "perfil", context.perfil(),
                    "hobby", Map.of(
                            "id", context.hobby().id(),
                            "nome", context.hobby().nome(),
                            "descricao", context.hobby().descricao(),
                            "custoEstimado", context.hobby().custoEstimado(),
                            "tempoNecessario", context.hobby().tempoNecessario(),
                            "nivelDificuldade", context.hobby().nivelDificuldade(),
                            "categoria", context.hobby().categoria(),
                            "tipoSocializacao", context.hobby().tipoSocializacao(),
                            "nivelAtividadeFisica", context.hobby().nivelAtividadeFisica(),
                            "ambiente", context.hobby().ambiente()
                    ),
                    "relacaoComHobby", context.relacaoComHobby()
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
}