package se.codecadence.routes.entities;

import org.springframework.hateoas.server.core.Relation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "buses")
@Relation(collectionRelation = "buses", itemRelation = "bus")
public class Bus {
    
    @Id
    @Column(name = "bus_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "max_seats")
    private Integer maxSeats; // max number of passengers
    
    @Column(name = "is_active")
    private Boolean isActive;

}
