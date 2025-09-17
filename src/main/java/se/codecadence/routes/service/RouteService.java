package se.codecadence.routes.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import se.codecadence.routes.entities.Route;
import se.codecadence.routes.repository.RoutesRepository;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final RoutesRepository routeRepository;

    public Page<Route> getRoutes(Pageable pageable) {
        return routeRepository.findAll(pageable);
    }

    public Route getRouteById(Long id) {
        return routeRepository.findById(id).orElse(null);
    }

    public Route createRoute(Route route) {
        return routeRepository.save(route);
    }

    public Route updateRoute(Long id, Route entity) {
        return routeRepository.findById(id).map(existingRoute -> {
            existingRoute.setName(entity.getName());
            existingRoute.setDescription(entity.getDescription());
            existingRoute.setFromDestination(entity.getFromDestination());
            existingRoute.setToDestination(entity.getToDestination());
            existingRoute.setDistance(entity.getDistance());
            existingRoute.setDuration(entity.getDuration());
            existingRoute.setAssignedBuses(entity.getAssignedBuses());
            return routeRepository.save(existingRoute);
        }).orElse(null);
    }

    public Optional<Route> deleteRoute(Long id) {
        return routeRepository.findById(id).map(existingRoute -> {
            routeRepository.delete(existingRoute);
            return existingRoute;
        });
    }

}
