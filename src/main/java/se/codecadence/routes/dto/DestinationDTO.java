package se.codecadence.routes.dto;

import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "destinations", itemRelation = "destination")
public record DestinationDTO(
    Long id,
    String name,
    String code, // e.g., airport code or station code
    String city,
    String country) {
}
