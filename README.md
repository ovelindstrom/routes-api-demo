# The Bus Routes API

This is a little demo project that is used to explain and describe how to do a couple of different things when it comes to designing nice REST APIs with Spring Boot.

The blog post where I describe the principles are:
- [Good habits when designing REST APIs](https://callistaenterprise.se/blogg/teknik/2025/09/03/bad-rest/)
- [The Discovery of REST APIs](https://callistaenterprise.se/blogg/teknik/2025/09/17/discoverable-apis)


## Open Telemetry and Grafana

I am using Open Telemetry to showcase logging and metrics.

If you run this example, first start the All-in-one Open Telemetry, not in any way production worthy but useful for local development, Grafana image.

```sh 
podman run -p 3000:3000 -p 4317:4317 -p 4318:4318 --rm -ti grafana/otel-lgtm
```

And then just open http://localhost:3000


