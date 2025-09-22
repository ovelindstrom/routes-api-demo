package se.codecadence.routes.dto;

import org.springframework.hateoas.server.core.Relation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Relation(collectionRelation = "buses", itemRelation = "bus")
public class BusDTO {
    
    private Long id;
    private String name;
    private Integer maxSeats; // max number of passengers
    private Boolean isActive;

}
