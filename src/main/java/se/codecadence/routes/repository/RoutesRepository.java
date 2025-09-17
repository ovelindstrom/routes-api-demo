package se.codecadence.routes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.codecadence.routes.entities.Route;

public interface RoutesRepository extends JpaRepository<Route, Long> {
	// Additional query methods (if needed) can be defined here
}
