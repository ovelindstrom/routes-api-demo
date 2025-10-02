package se.codecadence.routes;

import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.github.javafaker.Faker;
import com.github.javafaker.service.RandomService;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import se.codecadence.routes.dao.BusRepository;
import se.codecadence.routes.dao.DestinationRepository;
import se.codecadence.routes.dao.RoutesRepository;
import se.codecadence.routes.entities.Bus;
import se.codecadence.routes.entities.Destination;
import se.codecadence.routes.entities.Route;

@ApplicationScoped
public class InitDataRunner {

    Logger logger = Logger.getLogger(InitDataRunner.class.getName());

    enum GEN_SIZE {
        NONE(0,0,0),SMALL(10, 8, 10), MEDIUM(100, 88, 100), LARGE(1000, 888, 1000), HUGE(100_000, 8888, 100_000);

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

    @Inject
    private RoutesRepository routesRepository;
    @Inject
    private DestinationRepository destinationRepository;
    @Inject
    private BusRepository busRepository;

    @Inject
    @ConfigProperty(name = "app.init.size", defaultValue = "MEDIUM")
    private String genSizeParam;



    @PostConstruct
    public void init() {
        // Initialization logic can go here if needed
        logger.info("InitDataRunner started with genSizeParam: " + genSizeParam);

        // Default to MEDIUM if no argument is provided

        GEN_SIZE genSize = GEN_SIZE.MEDIUM;
        if (!genSizeParam.isEmpty()) {
            try {
                // Access the first non-option argument
                genSize = GEN_SIZE.valueOf(genSizeParam.toUpperCase());
                logger.info("Using data size: " + genSize);
            } catch (IllegalArgumentException e) {
                logger.warning("Invalid GEN_SIZE argument. Using default MEDIUM size.");
            }
        } else {
            logger.info("No GEN_SIZE argument provided. Using default MEDIUM size.");
        }

        if (genSize == GEN_SIZE.NONE) {
            logger.info("GEN_SIZE is NONE. No data will be generated.");
            return;
            
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
 