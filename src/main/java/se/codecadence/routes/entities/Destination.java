package se.codecadence.routes.entities;


import java.io.Serializable;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "destinations")
public class Destination implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonbProperty("destination_id")
    private Long id;

    @JsonbProperty("name")
    private String name;

    @JsonbProperty("code")
    private String code; // e.g., airport code or station code

    @JsonbProperty("city")
    private String city;

    @JsonbProperty("country")
    private String country;

}
