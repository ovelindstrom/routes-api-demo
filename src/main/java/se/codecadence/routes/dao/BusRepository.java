package se.codecadence.routes.dao;

import java.util.List;
import java.util.Optional;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import se.codecadence.routes.entities.Bus;

@RequestScoped
public class BusRepository {

    @PersistenceContext(name = "routes-api-pu")
    private EntityManager entityManager;

    // Find bus by ID
    public Optional<Bus> findById(Long id) {
        Bus bus = entityManager.find(Bus.class, id);
        return Optional.ofNullable(bus);
    }

    // Save or update a bus
    public Bus save(Bus bus) {
        if (bus.getId() == null) {
            entityManager.persist(bus);
            return bus;
        } else {
            return entityManager.merge(bus);
        }
    }

    // Delete a bus
    public void delete(Long id) {
        Bus bus = entityManager.find(Bus.class, id);
        if (bus != null) {
            entityManager.remove(bus);
        }
    }

    // Find all Buses
    public List<Bus> findAll() {
        return entityManager.createQuery("SELECT b FROM Bus b", Bus.class).getResultList();
    }
}