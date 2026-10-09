package dev.jamal.projetotcc.Exception;

public class OverpassUnavailableException extends RuntimeException {

    public OverpassUnavailableException() {
        super("O serviço de localização está temporariamente indisponível. Tente novamente em alguns instantes.");
    }
}