package dev.jamal.projetotcc.Exception;

import java.time.LocalDateTime;

public record ApiErrorResponse (LocalDateTime timestamp,
                                Integer status,
                                String error,
                                String path){
}
