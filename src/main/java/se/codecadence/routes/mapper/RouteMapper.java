package se.codecadence.routes.mapper;

import java.time.Duration;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import se.codecadence.routes.service.DestinationService;
import se.codecadence.routes.dto.RouteDTO;
import se.codecadence.routes.dto.RouteRequestDTO;
import se.codecadence.routes.entities.Route;
import se.codecadence.routes.service.BusService;

@Service
@AllArgsConstructor
public class RouteMapper {

    private final BusService busService;
    private final DestinationService destinationService;

    public Route toEntity(RouteRequestDTO dto) {
        Route route = new Route();
        route.setName(dto.name());
        route.setDescription(dto.description());
        route.setDistance(dto.distanceInKm());
        route.setDuration(Duration.ofMinutes(dto.durationInMinutes()));
        
        route.setFromDestination(destinationService.getDestinationById(dto.fromDestinationId()));
        route.setToDestination(destinationService.getDestinationById(dto.toDestinationId()));
        route.setAssignedBuses(dto.assignedBusIds().stream()
                .map(busId -> busService.getBusById(busId))
                .collect(Collectors.toList()));
        return route;
    }

    public RouteDTO toDTO(Route route) {
        return new RouteDTO(
            route.getId(),
            route.getName(),
            route.getDescription(),
            route.getFromDestination().getName(),
            route.getToDestination().getName(),
            route.getDistance(),
            route.getDuration(),
            route.getAssignedBuses().stream()
                .map(bus -> bus.getName())
                .collect(Collectors.toList()),
            route.getMaxNumberOfPassengers()
        );
    }

}
