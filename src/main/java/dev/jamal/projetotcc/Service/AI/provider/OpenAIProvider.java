package dev.jamal.projetotcc.Service.AI.provider;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.Map;

@Service
public class OpenAIProvider implements AIProvider {

    private final RestClient restClient;
    private final String model;

    public OpenAIProvider(
            @Value("${openai.api.key}") String apiKey,
            @Value("${openai.model}") String model
    ) {

        this.model = model;

        this.restClient = RestClient.builder()
                .baseUrl("https://api.openai.com/v1")
                .defaultHeader(
                        "Authorization",
                        "Bearer " + apiKey
                )
                .build();
    }

    @Override
    public String generate(String prompt) {

        Map<String, Object> body = Map.of(
                "model", model,
                "input", prompt
        );

        try {

            JsonNode response = restClient.post()
                    .uri("/responses")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(body)
                    .retrieve()
                    .body(JsonNode.class);

            return extrairTexto(response);

        } catch (org.springframework.web.client.RestClientResponseException e) {

            System.out.println("STATUS OPENAI: " + e.getStatusCode());
            System.out.println("RESPOSTA OPENAI:");
            System.out.println(e.getResponseBodyAsString());

            throw e;
        }
    }

    private String extrairTexto(JsonNode response) {

        if (response == null) {
            throw new RuntimeException(
                    "A API de IA retornou uma resposta vazia."
            );
        }

        JsonNode output = response.path("output");

        for (JsonNode item : output) {

            JsonNode content = item.path("content");

            for (JsonNode contentItem : content) {

                if ("output_text".equals(
                        contentItem.path("type").asText()
                )) {
                    return contentItem
                            .path("text")
                            .asText();
                }
            }
        }

        throw new RuntimeException(
                "Não foi possível extrair o texto da resposta da IA."
        );
    }
}