# The Bus Routes API

This is a little demo project that is used to explain and describe how to do a couple of different things when it comes to designing nice REST APIs with Spring Boot.

The blog post where I describe the principles are:
- [and the REST is history](https://callistaenterprise.se/blogg/teknik/2025/08/25/the-rest-is-history/)
- [Good habits when designing REST APIs](https://callistaenterprise.se/blogg/teknik/2025/09/03/bad-rest/)
- [The Discovery of REST APIs](https://callistaenterprise.se/blogg/teknik/2025/09/17/discoverable-apis)


## HAL Explorer

The POM entry `<artifactId>spring-data-rest-hal-explorer</artifactId>` adds a nice little UI that can be used to explore the API and play around with it. It is found under http://localhost:8080/explorer/index.html#uri=http://localhost:8080/api/v1

## Open Telemetry and Grafana

I am using Open Telemetry to showcase logging and metrics.

If you run this example, first start the All-in-one Open Telemetry, not in any way production worthy but useful for local development, Grafana image.

```sh 
podman run -p 3000:3000 -p 4317:4317 -p 4318:4318 --rm -ti grafana/otel-lgtm
```

And then just open http://localhost:3000

It is going to nag you about changing the default password.

## The structure of the repo

The `main` branch contains the sum of all blog posts in one. If you want to see what I have done for a specific blog post only, you can take a look at the branches. They are named as the blogposts series.

For the REST-series, the Routes example is found on the brach `discoverable-apis`.