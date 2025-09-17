package se.codecadence.routes.entities;

import java.time.Duration;
import java.util.List;

import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "routes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Relation(collectionRelation = "routes", itemRelation = "route")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonAlias("routeId")
    private Long id;
    private String name;
    private String description;
    
    @ManyToOne
    private Destination fromDestination;
    @ManyToOne
    private Destination toDestination;

    private Double distance; // in kilometers
    private Duration duration; // in minutes

    @ManyToMany
    private List<Bus> assignedBuses = new ArrayList<>();

    // Additional fields and methods can be added as needed
    public Integer getMaxNumberOfPassengers() {
        return assignedBuses.stream()
                .mapToInt(Bus::getMaxSeats)
                .sum();
    }

}
