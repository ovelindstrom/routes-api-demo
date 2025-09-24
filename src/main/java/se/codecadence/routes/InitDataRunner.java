package se.codecadence.routes;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;
import com.github.javafaker.service.RandomService;

import se.codecadence.routes.entities.Bus;
import se.codecadence.routes.entities.Destination;
import se.codecadence.routes.entities.Route;
import se.codecadence.routes.repository.BusRepository;
import se.codecadence.routes.repository.DestinationRepository;
import se.codecadence.routes.repository.RoutesRepository;

@Component
public class InitDataRunner implements CommandLineRunner {

    Logger logger = Logger.getLogger(InitDataRunner.class.getName());

    enum GEN_SIZE {
        SMALL(10, 8, 10), MEDIUM(100, 88, 100), LARGE(1000, 888, 1000), HUGE(100_000, 8888, 100_000);

        private final int routeCount;
        private final int busCount;
        private final int destinationCount;

        GEN_SIZE(int routeCount, int busCount, int destinationCount) {
            this.routeCount = routeCount;
            this.busCount = busCount;
            this.destinationCount = destinationCount;
        }

        public int getRouteCount() {
            return routeCount;
        }

        public int getBusCount() {
            return busCount;
        }

        public int getDestinationCount() {
            return destinationCount;
        }
    }

    private final ApplicationArguments appArgs;
    private RoutesRepository routesRepository;
    private DestinationRepository destinationRepository;
    private BusRepository busRepository;

    public InitDataRunner(ApplicationArguments appArgs, RoutesRepository routesRepository,
            DestinationRepository destinationRepository, BusRepository busRepository) {
        this.appArgs = appArgs;
        this.routesRepository = routesRepository;
        this.destinationRepository = destinationRepository;
        this.busRepository = busRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Initialization logic can go here if needed
        // Get non-option arguments. Your 'small', 'medium', or 'large' will be here.
        List<String> nonOptionArgs = appArgs.getNonOptionArgs();

        // Default to MEDIUM if no argument is provided

        GEN_SIZE genSize = GEN_SIZE.MEDIUM;
        if (!nonOptionArgs.isEmpty()) {
            try {
                // Access the first non-option argument
                genSize = GEN_SIZE.valueOf(nonOptionArgs.get(0).toUpperCase());
                logger.info("Using data size: " + genSize);
            } catch (IllegalArgumentException e) {
                logger.warning("Invalid GEN_SIZE argument. Using default MEDIUM size.");
            }
        } else {
            logger.info("No GEN_SIZE argument provided. Using default MEDIUM size.");
        }

        Faker faker = new Faker(Locale.of("sv"), new RandomService());

        // Generate and save 100 sample Destinations
        for (int i = 1; i <= genSize.destinationCount; i++) {
            var fakeDestination = faker.address();
            Destination destination = new Destination();
            destination.setName(fakeDestination.cityName());
            destination.setCity(fakeDestination.city());
            destination.setCountry(fakeDestination.country());
            destination.setCode("CODE-" + fakeDestination.zipCode());
            destinationRepository.save(destination);
        }

        // Generate and save 88 sample Buses

        for (int i = 1; i <= genSize.busCount; i++) {
            Bus bus = new Bus();
            bus.setName(faker.regexify("[A-Z]{3}-\\d{3}"));
            bus.setMaxSeats(40 + i);
            bus.setIsActive(true);
            busRepository.save(bus);
        }

        // Generate and save 100 sample Routes, each linked to a random Destination and
        // Bus
        for (int i = 1; i <= genSize.routeCount; i++) {
            Route route = new Route();

            var fromDestination = destinationRepository
                    .findById((long) (ThreadLocalRandom.current().nextDouble() * genSize.destinationCount) + 1)
                    .orElse(null);
            var toDestination = destinationRepository
                    .findById((long) (ThreadLocalRandom.current().nextDouble() * genSize.destinationCount) + 1)
                    .orElse(null);

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
                Bus bus = busRepository
                        .findById((long) (ThreadLocalRandom.current().nextDouble() * genSize.busCount) + 1)
                        .orElse(null);
                if (bus != null && !route.getAssignedBuses().contains(bus)) {
                    route.getAssignedBuses().add(bus);
                }
            }
            routesRepository.save(route);
        }
    }
}
