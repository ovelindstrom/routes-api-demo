package se.codecadence.routes.dao;

import java.util.List;
import java.util.Optional;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import se.codecadence.routes.entities.Route;

@RequestScoped
public class RoutesRepository {

	// Inject your EntityManager or equivalent here
	@PersistenceContext(name = "routes-api-pu")
    private EntityManager entityManager;

	public List<Route> findAll() {
		return entityManager.createQuery("SELECT r FROM Route r", Route.class).getResultList();
	}
	// Implement repository methods here

	public Optional<Route> findById(Long id) {
		return Optional.ofNullable(entityManager.find(Route.class, id));
	}

	public Route save(Route route) {
		entityManager.persist(route);
		return route;
	}

	public void delete(Route existingRoute) {
		entityManager.remove(existingRoute);
	}
}
