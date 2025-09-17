package se.codecadence.routes.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import se.codecadence.routes.entities.Destination;
import se.codecadence.routes.repository.DestinationRepository;

@Service
@AllArgsConstructor
public class DestinationService {

    private final DestinationRepository destinationRepository;

    public Page<Destination> getDestinations(Pageable pageable) {
        return destinationRepository.findAll(pageable);
    }

    public Destination getDestinationById(Long id) {
        return destinationRepository.findById(id).orElse(null);
    }

}
