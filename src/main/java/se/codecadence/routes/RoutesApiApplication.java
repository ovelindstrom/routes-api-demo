package se.codecadence.routes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.CommandLineRunner;

import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.context.annotation.Bean;

import com.github.javafaker.Faker;
import com.github.javafaker.service.RandomService;

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

			Faker faker = new Faker(Locale.of("sv"), new RandomService());

			// Generate and save 100 sample Destinations
			for (int i = 1; i <= 100; i++) {
				var fakeDestination = faker.address();
				Destination destination = new Destination();
				destination.setName(fakeDestination.cityName());
				destination.setCity(fakeDestination.city());
				destination.setCountry(fakeDestination.country());
				destination.setCode("CODE-" + fakeDestination.zipCode());
				destinationRepository.save(destination);
			}

			// Generate and save 88 sample Buses
	
			for (int i = 1; i <= 88; i++) {
				Bus bus = new Bus();
				bus.setName(faker.regexify("[A-Z]{3}-\\d{3}"));
				bus.setMaxSeats(40 + i);
				bus.setIsActive(true);
				busRepository.save(bus);
			}

			// Generate and save 100 sample Routes, each linked to a random Destination and Bus
			for (int i = 1; i <= 10; i++) {
				Route route = new Route();

				var fromDestination = destinationRepository.findById((long) (ThreadLocalRandom.current().nextDouble() * 100) + 1).orElse(null);
				var toDestination = destinationRepository.findById((long) (ThreadLocalRandom.current().nextDouble() * 100) + 1).orElse(null);
			
				route.setName((fromDestination.getCity() + " : " + toDestination.getCity()));
				route.setDescription("From " + fromDestination.getCity() + " to " + toDestination.getCity());
				route.setDistance(50.0 + i * 10); // example distance
				route.setDuration(java.time.Duration.ofMinutes(60 + i * 15)); //
				route.setFromDestination(fromDestination);
				route.setToDestination(toDestination);

				// Get a random number 1 to 3

				int numberOfBuses = (int) (ThreadLocalRandom.current().nextDouble() * 8) + 1;
				// Get that many random buses and add to a List of buses
				for (int j = 0; j < numberOfBuses; j++) {
					Bus bus = busRepository.findById((long) (ThreadLocalRandom.current().nextDouble() * 88) + 1).orElse(null);
					if (bus != null && !route.getAssignedBuses().contains(bus)) {
						route.getAssignedBuses().add(bus);
					}
				}
				routesRepository.save(route);
			}
		};
	}
}
