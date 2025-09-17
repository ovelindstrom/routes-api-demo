package se.codecadence.routes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import se.codecadence.routes.entities.Bus;

public interface BusRepository extends JpaRepository<Bus, Long> {

}
