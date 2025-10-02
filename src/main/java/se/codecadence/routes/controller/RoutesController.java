package se.codecadence.routes.controller;

import java.util.List;
import java.util.Optional;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import se.codecadence.routes.dto.RouteDTO;
import se.codecadence.routes.dto.RouteRequestDTO;
import se.codecadence.routes.entities.Route;
import se.codecadence.routes.mapper.RouteMapper;
import se.codecadence.routes.service.RouteService;

@RequestScoped
@Path("/v1/routes")
public class RoutesController {

    @Inject
    private  RouteService routeService;
    @Inject
    private  RouteMapper routeMapper;
    
    /**
     * Retrieves all routes.
     *
     * @return a {@link ResponseEntity} containing a list of {@link RouteDTO} objects.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoutes() {

        List<Route> routes = routeService.getRoutes();

        List<RouteDTO> routeDTOs = routes.stream().map(routeMapper::toDTO).toList();
        return Response.ok(routeDTOs).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRouteById(
            @jakarta.ws.rs.PathParam("id") Long id,
            @jakarta.ws.rs.QueryParam("embed") String embed) {
        Route route = routeService.getRouteById(id);
        if (route == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        RouteDTO dto = routeMapper.toDTO(route);

        // You may need to adjust the following HAL/links code to be compatible with JAX-RS or remove it if not supported.
        // For now, return the DTO as a simple JSON response.
        return Response.ok(dto).build();
    }

    // Create Route
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRoute(RouteRequestDTO dto) {

        Route createdRoute = routeService.createRoute(routeMapper.toEntity(dto));
        java.net.URI location = jakarta.ws.rs.core.UriBuilder
            .fromPath("/api/v1/routes/{id}")
            .build(createdRoute.getId());
        return Response.created(location).entity(routeMapper.toDTO(createdRoute)).build();
    }

    // Update Route

    //@PutMapping(path = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateRoute(@PathParam("id") Long id, RouteRequestDTO dto) {
        Route updatedRoute = routeService.updateRoute(id, routeMapper.toEntity(dto));
        if (updatedRoute == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        RouteDTO routeDTO = routeMapper.toDTO(updatedRoute);


        return Response.ok(routeDTO).build();
    }

    // Delete Route
    //@DeleteMapping(path = "/{id}")
    @DELETE  
    @Path("/{id}")
    public Response deleteRoute(@PathParam("id") Long id) {
        Optional<Route> existingRoute = routeService.deleteRoute(id);
        if (existingRoute.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }

}
