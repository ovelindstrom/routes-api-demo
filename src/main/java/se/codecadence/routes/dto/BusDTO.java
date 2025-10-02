package se.codecadence.routes.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusDTO {
    
    private Long id;
    private String name;
    private Integer maxSeats; // max number of passengers
    private Boolean isActive;

}
