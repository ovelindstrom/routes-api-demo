package se.codecadence.routes.mapper;


import jakarta.enterprise.context.ApplicationScoped;
import se.codecadence.routes.dto.DestinationDTO;
import se.codecadence.routes.entities.Destination;

@ApplicationScoped
public class DestinationMapper {
    
    public DestinationDTO toDTO(Destination destination) {
        return new DestinationDTO(
            destination.getId(),
            destination.getName(),
            destination.getCode(),
            destination.getCity(),
            destination.getCountry()
        );
    }

}
