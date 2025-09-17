package se.codecadence.routes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import se.codecadence.routes.entities.Destination;

public interface DestinationRepository extends JpaRepository<Destination, Long> {

}
