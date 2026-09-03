package dev.jamal.projetotcc.Exception;

import lombok.Getter;

@Getter
public class AIProviderException extends RuntimeException {

    private final int status;

    public AIProviderException(
            String message,
            int status
    ) {
        super(message);
        this.status = status;
    }
}