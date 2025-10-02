package se.codecadence.routes.controller;

import java.util.List;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import se.codecadence.routes.dto.DestinationDTO;
import se.codecadence.routes.entities.Destination;
import se.codecadence.routes.mapper.DestinationMapper;
import se.codecadence.routes.service.DestinationService;



@RequestScoped
@Path("/v1/destinations")
public class DestinationController {

        @Inject
    private  DestinationService destinationService;
        @Inject
    private DestinationMapper destinationMapper;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDestinations() {


        List<Destination> destinations = destinationService.getDestinations();
        List<DestinationDTO> destinationDTOs = destinations.stream()
            .map(destinationMapper::toDTO)
            .toList();
        return Response.ok(destinationDTOs).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDestinationById(@PathParam("id") Long id) {
        Destination destination = destinationService.getDestinationById(id);
        if (destination == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(destinationMapper.toDTO(destination)).build();
    }

}
