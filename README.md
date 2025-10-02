# The Bus Routes API - Open Liberty version

This is a little demo project that is used to explain and describe how to do a couple of different things when it comes to designing nice REST APIs with Spring Boot.

The blog post where I describe the principles are:
- [and the REST is history](https://callistaenterprise.se/blogg/teknik/2025/08/25/the-rest-is-history/)
- [Good habits when designing REST APIs](https://callistaenterprise.se/blogg/teknik/2025/09/03/bad-rest/)
- [The Discovery of REST APIs](https://callistaenterprise.se/blogg/teknik/2025/09/17/discoverable-apis/)

## Description

A demo project for building robust and well-designed REST APIs. This application showcases best practices for API development, observability with Micrometer and OpenTelemetry, and security scanning with OWASP Dependency-Check.

**This version is using Jakarta EE 10 and Open Liberty 25 as a server.**

## Features

- **RESTful Endpoints**: Full CRUD operations for routes, buses, and destinations.
- **Validation**: Bean validation on request DTOs.
- **Security Scanning**: OWASP Dependency-Check fails builds on high-severity vulnerabilities (CVSS >= 7.0).
- **In-Memory Database**: DerbyDB for easy testing and development.
- **Actuators**: Health checks.

## Prerequisites

- Java 21
- Maven 3.6+
- (Optional) Postman for API testing

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/ovelindstrom/routes-api-demo.git
   cd routes-api-demo
   ```

2. Build the project:
   ```bash
   ./mvnw clean install
   ```

## Running the Application

### Basic Run
```bash
./mvnw liberty:run
```

### Dev Server
```bash
./mvnw liberty:dev
```

### Run with Size Argument (for demo data initialization)
** Currently the init does not trigger for some reason **
Pass a size argument to control the amount of initial data:
- `NONE`: No data
- `SMALL`: Minimal data
- `MEDIUM`: Moderate data
- `LARGE`: Extensive data
- `HUGE`: Ridiculous amounts of data

```bash
./mvnw liberty:run -Dspring-boot.run.arguments="--size=LARGE"
```

The Open Liberty runs the following web applications:
JTW Endpoint          http://localhost:9080/jwt/
Open API dictionary   http://localhost:9080/openapi/
Open API UI           http://localhost:9080/openapi/ui/
Health Check          http://localhost:9080/health/
Routes API            http://localhost:9080/routes/


## Usage

### API Endpoints

- **Routes**:
  - `GET /api/v1/routes` - List all routes 
  - `GET /api/v1/routes/{id}` - Get route by ID
  - `POST /api/v1/routes` - Create a new route
  - `PUT /api/v1/routes/{id}` - Update a route
  - `DELETE /api/v1/routes/{id}` - Delete a route

- **Buses**:
  - `GET /api/v1/buses` - List buses (paginated)
  - `GET /api/v1/buses/{id}` - Get bus by ID
  - `POST /api/v1/buses` - Create a new bus

- **Destinations**:
  - `GET /api/v1/destinations` - List destinations (paginated)
  - `GET /api/v1/destinations/{id}` - Get destination by ID

### Example Request Body for Creating a Route
```json
{
  "name": "Stockholm to Gothenburg",
  "description": "Main route between major cities",
  "fromDestinationId": 1,
  "toDestinationId": 2,
  "distanceInKm": 450.0,
  "durationInMinutes": 360,
  "assignedBusIds": [1, 2]
}
```

### Postman Collection
Import the `Routes.postman_collection.json` file into Postman for easy API testing.

### Actuators
- Health: `http://localhost:8080/actuator/health`
- Metrics: `http://localhost:8080/actuator/metrics`
- Info: `http://localhost:8080/actuator/info`

## Open Telemetry and Grafana

I am using Open Telemetry to showcase logging and metrics.

If you run this example, first start the All-in-one Open Telemetry, not in any way production worthy but useful for local development, Grafana image.

```sh
podman network create otel-dev-net
podman run --name otel --network otel-dev-net -p 3000:3000 -p 4317:4317 -p 4318:4318 -ti grafana/otel-lgtm
```

And then just open http://localhost:3000

It is going to nag you about changing the default password.

## Configuration

Key properties in `application.properties`:
- Database: H2 in-memory (`jdbc:h2:mem:routesdb`)
- JPA: DDL auto-create-drop
- OTLP Metrics: Enabled for OpenTelemetry export

## Development

### Running Tests
```bash
./mvnw test
```

### Security Scanning
The build includes OWASP Dependency-Check, which will fail if vulnerabilities with CVSS >= 7.0 are found. Run manually:
```bash
./mvnw dependency-check:check
```


### Open Liberty

Open a command line session, navigate to the installation directory, and run `./mvnw liberty:dev` (Linux/Mac) or `mvnw liberty:dev` (Windows). 

### Docker image

```bash
podman build -t routes-api .
podman run -p 9080:9080 routes-api
 ```

## The structure of the repo

The `main` branch contains the sum of all blog posts in one. If you want to see what I have done for a specific blog post only, you can take a look at the branches. They are named as the blogposts series.

For the REST-series, the Routes example is found on the brach `discoverable-apis`.

## Contributing

Contributions are welcome! Please follow these steps:
1. Fork the repository.
2. Create a feature branch.
3. Make your changes and add tests.
4. Submit a pull request.

## License

This project is licensed under the Creative Commons Attribution 4.0 International Public License (CC BY 4.0). See the [LICENSE](LICENSE) file for details.

## Author

- **Ove Lindström** - [Code & Cadence Blog](https://codecadence.se) - ove.lindstrom@codecadence.se

For more information, visit the [GitHub repository](https://github.com/ovelindstrom/routes-api-demo).