package se.codecadence.routes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateBusResponseDTO(
    @JsonProperty("bus_id") Long id,
    String name,
    Integer capacity,
    @JsonProperty("is_active") Boolean isActive
) {}