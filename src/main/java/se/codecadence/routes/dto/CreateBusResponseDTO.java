package se.codecadence.routes.dto;

import jakarta.json.bind.annotation.JsonbProperty;

public record CreateBusResponseDTO(

    @JsonbProperty("bus_id") Long id,
    String name,
    Integer capacity,
    @JsonbProperty("is_active") Boolean isActive
) {}