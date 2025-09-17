package se.codecadence.routes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import se.codecadence.routes.entities.Bus;
import se.codecadence.routes.entities.Destination;
import se.codecadence.routes.entities.Route;
import se.codecadence.routes.repository.BusRepository;
import se.codecadence.routes.repository.DestinationRepository;
import se.codecadence.routes.repository.RoutesRepository;

@SpringBootApplication
public class RoutesApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoutesApiApplication.class, args);
	}

	@Bean
	public CommandLineRunner initRoutesData(RoutesRepository routesRepository, DestinationRepository destinationRepository, BusRepository busRepository) {
		return args -> {
			// Generate and save 100 sample Destinations
			for (int i = 1; i <= 100; i++) {
				Destination destination = new Destination();
				destination.setName("Destination " + i);
				destination.setCity("City of " + i);
				destination.setCountry("Country of " + i);
				destination.setCode("CODE-" + i);
				destinationRepository.save(destination);
			}

			// Generate and save 88 sample Buses
			for (int i = 1; i <= 88; i++) {
				Bus bus = new Bus();
				bus.setName("ABC-" + (100 + i));
				bus.setMaxSeats(40 + i);
				bus.setIsActive(true);
				busRepository.save(bus);
			}

			// Generate and save 100 sample Routes, each linked to a random Destination and Bus
			for (int i = 1; i <= 10; i++) {
				Route route = new Route();
				route.setName("Route " + i);
				route.setDescription("Description for Route " + i);
				route.setDistance(50.0 + i * 10); // example distance
				route.setDuration(java.time.Duration.ofMinutes(60 + i * 15)); //
				route.setFromDestination(destinationRepository.findById((long) (Math.random() * 100) + 1).orElse(null));
				route.setToDestination(destinationRepository.findById((long) (Math.random() * 100) + 1).orElse(null));

				// Get a random number 1 to 3

				int numberOfBuses = (int) (Math.random() * 8) + 1;
				// Get that many random buses and add to a List of buses
				for (int j = 0; j < numberOfBuses; j++) {
					Bus bus = busRepository.findById((long) (Math.random() * 88) + 1).orElse(null);
					if (bus != null && !route.getAssignedBuses().contains(bus)) {
						route.getAssignedBuses().add(bus);
					}
				}
				routesRepository.save(route);
			}
		};
	}
}
