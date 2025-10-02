package se.codecadence.routes.service;

import java.util.List;
import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import se.codecadence.routes.dao.RoutesRepository;
import se.codecadence.routes.entities.Route;

@ApplicationScoped
public class RouteService {

    @Inject
    private RoutesRepository routeRepository;

    public List<Route> getRoutes() {
        return routeRepository.findAll();
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
