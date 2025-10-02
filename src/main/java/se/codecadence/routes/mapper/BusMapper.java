package se.codecadence.routes.mapper;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.constraints.NotNull;
import se.codecadence.routes.dto.BusDTO;
import se.codecadence.routes.dto.CreateBusRequestDTO;
import se.codecadence.routes.dto.CreateBusResponseDTO;
import se.codecadence.routes.entities.Bus;

@ApplicationScoped
public class BusMapper {

    public BusDTO toBusDTO(@NotNull Bus bus) {
        return new BusDTO(
                bus.getId(),
                bus.getName(),
                bus.getMaxSeats(),
                bus.getIsActive());
    }

    public Bus toBus(@NotNull CreateBusRequestDTO createBusRequestDTO) {
        Bus bus = new Bus();
        bus.setName(createBusRequestDTO.name());
        bus.setMaxSeats(createBusRequestDTO.capacity());
        bus.setIsActive(true); // New buses are active by default
        return bus;
    }

    public CreateBusResponseDTO toCreateBusResponseDTO(@NotNull Bus bus) {
        return new CreateBusResponseDTO(
                bus.getId(),
                bus.getName(),
                bus.getMaxSeats(),
                bus.getIsActive());
    }

}
