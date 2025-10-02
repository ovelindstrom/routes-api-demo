package se.codecadence.routes.controller;


import java.util.List;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import se.codecadence.routes.entities.Bus;
import se.codecadence.routes.service.BusService;

@RequestScoped
@Path("/v1/buses")
public class BusController {

    @Inject
    private BusService busService;

    @Context
    UriInfo uriInfo;



    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBuses() {
        List<Bus> busses = busService.getBuses();
        return Response.ok(busses).build();
    }

    @GET
    @Path("/{id}")
    public Response getBusById(@jakarta.ws.rs.PathParam("id") Long id) {
        Bus bus = busService.getBusById(id);
        if (bus == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(bus).build();
    }

}
