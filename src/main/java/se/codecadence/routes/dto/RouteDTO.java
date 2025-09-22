package se.codecadence.routes.dto;


import java.time.Duration;
import java.util.List;

import org.springframework.hateoas.server.core.Relation;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Relation(collectionRelation = "routes", itemRelation = "route")
public class RouteDTO {

    private Long id;
    private String name;
    private String description;
    private String fromDestination;
    private String toDestination;
    private Double distance; // in kilometers
    private Duration duration; // in minutes
    private List<String> assignedBuses;
    private Integer maxNumberOfPassengers;
    
}

