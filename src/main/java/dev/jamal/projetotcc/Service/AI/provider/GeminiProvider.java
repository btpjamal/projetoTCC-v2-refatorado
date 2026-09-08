package dev.jamal.projetotcc.Service.AI.provider;

import tools.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import dev.jamal.projetotcc.Exception.AIProviderException;

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

        int maxTentativas = 3;

        for (int tentativa = 1;
             tentativa <= maxTentativas;
             tentativa++) {

            try {

                JsonNode response =
                        chamarGemini(prompt);
                System.out.println(
                        response.path("usageMetadata").toPrettyString()
                );


                return extrairTexto(response);

            } catch (RestClientResponseException e) {

                int status =
                        e.getStatusCode().value();

                if (status == 503) {

                    if (tentativa == maxTentativas) {
                        throw new AIProviderException(
                                "O serviço de IA está temporariamente indisponível. Tente novamente em alguns instantes.",
                                503
                        );
                    }

                    esperarAntesDeTentarNovamente(tentativa);
                    continue;
                }

                if (status == 429) {
                    throw new AIProviderException(
                            "Limite de solicitações da IA atingido. Tente novamente mais tarde.",
                            429
                    );
                }

                if (status == 401 || status == 403) {
                    throw new AIProviderException(
                            "Não foi possível autenticar com o serviço de IA.",
                            503
                    );
                }

                throw new AIProviderException(
                        "Não foi possível gerar o plano personalizado.",
                        502
                );
            }
        }

        throw new AIProviderException(
                "Não foi possível gerar o plano personalizado.",
                503
        );
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

    private JsonNode chamarGemini(String prompt) {

        Map<String, Object> part =
                Map.of("text", prompt);

        Map<String, Object> content =
                Map.of("parts", List.of(part));

        Map<String, Object> body =
                Map.of("contents", List.of(content));

        return restClient.post()
                .uri(
                        "/models/{model}:generateContent?key={apiKey}",
                        model,
                        apiKey
                )
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
                .retrieve()
                .body(JsonNode.class);
    }

    private void esperarAntesDeTentarNovamente(
            int tentativa
    ) {

        long esperaMillis =
                tentativa == 1 ? 1000L : 2000L;

        try {
            Thread.sleep(esperaMillis);
        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            throw new AIProviderException(
                    "A geração do plano foi interrompida.",
                    503
            );
        }
    }
}