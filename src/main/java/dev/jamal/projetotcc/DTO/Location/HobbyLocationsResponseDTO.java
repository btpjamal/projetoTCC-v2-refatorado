package dev.jamal.projetotcc.DTO.Location;

import java.util.List;

public record HobbyLocationsResponseDTO(
        String cidade,
        String estado,
        Double latitude,
        Double longitude,
        List<HobbyLocationDTO> locais
) {
}
