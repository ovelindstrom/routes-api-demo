package se.codecadence.routes.dto;


import java.time.Duration;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

