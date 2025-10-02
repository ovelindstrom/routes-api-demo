package se.codecadence.routes.dto;


public record DestinationDTO(
    Long id,
    String name,
    String code, // e.g., airport code or station code
    String city,
    String country) {
}
