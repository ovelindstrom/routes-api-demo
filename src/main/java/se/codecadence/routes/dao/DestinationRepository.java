package se.codecadence.routes.dao;


import java.util.List;
import java.util.Optional;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import se.codecadence.routes.entities.Destination;

@RequestScoped
public class DestinationRepository  {
    
    // Inject your EntityManager or equivalent here
    @PersistenceContext(name = "routes-api-pu")
    private EntityManager entityManager;

    // Find all Destinations
    public List<Destination> findAll() {
        return entityManager.createQuery("SELECT d FROM Destination d", Destination.class).getResultList();
    }

    // Find Destination by ID
    public Optional<Destination> findById(Long id) {
        Destination destination = entityManager.find(Destination.class, id);
        return destination != null ? Optional.of(destination) : Optional.empty();
    }

    public void save(Destination destination) {
        entityManager.persist(destination);
    }

}
