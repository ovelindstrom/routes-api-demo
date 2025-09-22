package se.codecadence.routes.mapper;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import se.codecadence.routes.dto.DestinationDTO;
import se.codecadence.routes.entities.Destination;

@Service
@AllArgsConstructor
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
