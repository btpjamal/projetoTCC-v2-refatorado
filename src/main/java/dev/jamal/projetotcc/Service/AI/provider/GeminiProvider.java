package dev.jamal.projetotcc.Service.AI.provider;

import tools.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.util.List;
import java.util.Map;

@Service
@ConditionalOnProperty(
        name = "ai.provider",
        havingValue = "gemini"
)
public class GeminiProvider implements AIProvider {

    private final RestClient restClient;
    private final String apiKey;
    private final String model;

    public GeminiProvider(
            @Value("${gemini.api.key}") String apiKey,
            @Value("${gemini.model}") String model
    ) {
        this.apiKey = apiKey;
        this.model = model;

        this.restClient = RestClient.builder()
                .baseUrl(
                        "https://generativelanguage.googleapis.com/v1beta"
                )
                .build();
    }

    @Override
    public String generate(String prompt) {

        Map<String, Object> part = Map.of(
                "text", prompt
        );

        Map<String, Object> content = Map.of(
                "parts", List.of(part)
        );

        Map<String, Object> body = Map.of(
                "contents", List.of(content)
        );

        try {

            JsonNode response = restClient.post()
                    .uri(
                            "/models/{model}:generateContent?key={apiKey}",
                            model,
                            apiKey
                    )
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(body)
                    .retrieve()
                    .body(JsonNode.class);

            return extrairTexto(response);

        } catch (RestClientResponseException e) {

            System.out.println(
                    "STATUS GEMINI: " + e.getStatusCode()
            );

            System.out.println(
                    "RESPOSTA GEMINI:"
            );

            System.out.println(
                    e.getResponseBodyAsString()
            );

            throw e;
        }
    }

    private String extrairTexto(JsonNode response) {

        if (response == null) {
            throw new RuntimeException(
                    "A API Gemini retornou uma resposta vazia."
            );
        }

        JsonNode candidates =
                response.path("candidates");

        if (candidates.isEmpty()) {
            throw new RuntimeException(
                    "A API Gemini não retornou candidatos."
            );
        }

        JsonNode parts =
                candidates
                        .get(0)
                        .path("content")
                        .path("parts");

        for (JsonNode part : parts) {

            if (part.has("text")) {
                return part.path("text").asText();
            }
        }

        throw new RuntimeException(
                "Não foi possível extrair o texto da resposta do Gemini."
        );
    }
}