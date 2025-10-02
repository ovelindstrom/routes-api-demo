package se.codecadence.routes.service;
    
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import se.codecadence.routes.dao.BusRepository;
import se.codecadence.routes.entities.Bus;

@ApplicationScoped
public class BusService {

    @Inject
    private BusRepository busRepository;


    public List<Bus> getBuses() {
        return busRepository.findAll();
    }

    public Bus getBusById(Long id) {
        return busRepository.findById(id).orElse(null);
    }

}
