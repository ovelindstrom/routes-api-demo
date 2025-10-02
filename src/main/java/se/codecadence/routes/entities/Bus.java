package se.codecadence.routes.entities;

import java.io.Serializable;

import jakarta.json.bind.annotation.JsonbProperty;
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
public class Bus implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "bus_id")
    @JsonbProperty("bus_id")    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @JsonbProperty("name")  
    private String name;

    @Column(name = "max_seats")
    @JsonbProperty("max_seats")
    private Integer maxSeats; // max number of passengers
    
    @Column(name = "is_active")
    @JsonbProperty("is_active")
    private Boolean isActive;

}
