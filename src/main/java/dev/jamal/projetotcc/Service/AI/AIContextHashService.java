package dev.jamal.projetotcc.Service.AI;

import dev.jamal.projetotcc.DTO.AI.AIUserContext;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;

@Service
public class AIContextHashService {

    private final ObjectMapper objectMapper;

    public AIContextHashService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String calcular(AIUserContext context) {

        try {
            String json =
                    objectMapper.writeValueAsString(context);

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