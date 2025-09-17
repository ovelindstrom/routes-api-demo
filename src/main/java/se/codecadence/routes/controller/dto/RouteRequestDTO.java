package se.codecadence.routes.controller.dto;

import java.util.List;

import jakarta.validation.constraints.*;

public record RouteRequestDTO(

        @NotBlank @Size(min = 3, max = 100) String name,
        @NotBlank @Size(min = 3, max = 100) String description,
        @NotNull @PositiveOrZero Long fromDestinationId,
        @NotNull @PositiveOrZero Long toDestinationId,
        @NotNull @Positive Double distanceInKm,
        @NotNull @Positive Long durationInMinutes,
        @NotNull List<Long> assignedBusIds) {
}
