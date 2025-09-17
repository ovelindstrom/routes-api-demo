package se.codecadence.routes.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import se.codecadence.routes.entities.Bus;
import se.codecadence.routes.repository.BusRepository;

@Service
@AllArgsConstructor
public class BusService {

    private final BusRepository busRepository;

    public Page<Bus> getBuses(Pageable pageable) {
        return busRepository.findAll(pageable);
    }

    public Bus getBusById(Long id) {
        return busRepository.findById(id).orElse(null);
    }

}
