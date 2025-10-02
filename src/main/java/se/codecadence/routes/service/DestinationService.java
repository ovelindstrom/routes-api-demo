package se.codecadence.routes.service;


import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import se.codecadence.routes.dao.DestinationRepository;
import se.codecadence.routes.entities.Destination;

@ApplicationScoped
public class DestinationService {

    @Inject
    private DestinationRepository destinationRepository;

    public List<Destination> getDestinations() {
        return destinationRepository.findAll();
    }

    public Destination getDestinationById(Long id) {
        return destinationRepository.findById(id).orElse(null);
    }

}
