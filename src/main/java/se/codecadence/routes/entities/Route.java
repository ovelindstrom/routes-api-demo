package se.codecadence.routes.entities;

import java.io.Serializable;
import java.time.Duration;
import java.util.List;

import java.util.ArrayList;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Routes")
@NamedQuery(name = "Route.findAll", query = "SELECT r FROM Route r")
@NamedQuery(name = "Route.findById", query = "SELECT r FROM Route r WHERE r.id = :id")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Route implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonbProperty("routeId")
    private Long id;
    @JsonbProperty("name")
    private String name;
    @JsonbProperty("description")
    private String description;
    
    @ManyToOne
    @JsonbProperty("fromDestination")
    private Destination fromDestination;

    @ManyToOne
    @JsonbProperty("toDestination")
    private Destination toDestination;

    @JsonbProperty("distance")
    private Double distance; // in kilometers
    @JsonbProperty("duration")
    private Duration duration; // in minutes

    @ManyToMany
    @JsonbProperty("assignedBuses")
    private List<Bus> assignedBuses = new ArrayList<>();

    // Additional fields and methods can be added as needed
    public Integer getMaxNumberOfPassengers() {
        return assignedBuses.stream()
                .mapToInt(Bus::getMaxSeats)
                .sum();
    }

}
