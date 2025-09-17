package se.codecadence.routes.controller;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.mediatype.hal.HalModelBuilder;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import se.codecadence.routes.controller.dto.RouteDTO;
import se.codecadence.routes.controller.dto.RouteRequestDTO;
import se.codecadence.routes.entities.Route;
import se.codecadence.routes.entities.RouteMapper;
import se.codecadence.routes.service.RouteService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.afford;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/routes")
@AllArgsConstructor
public class RoutesController {

    private final RouteService routeService;
    private final RouteMapper routeMapper;

    private final static Logger LOGGER = Logger.getLogger(RoutesController.class.getName());

    /**
     * Retrieves a paginated list of routes.
     * 
     * @param pageable  the pagination information. Use page, size, sort parameters. 
     *                  See <a href="https://docs.spring.io/spring-data/rest/reference/paging-and-sorting.html">Spring Data REST - Paging and Sorting</a>
     * @return a ResponseEntity containing the paginated list of routes
     */
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<PagedModel<EntityModel<RouteDTO>>> getRoutes(Pageable pageable,
            PagedResourcesAssembler<Route> assembler) {

        Page<Route> routePage = routeService.getRoutes(pageable);

        PagedModel<EntityModel<RouteDTO>> routesModel = assembler.toModel(routePage, route -> {
            RouteDTO dto = routeMapper.toDTO(route);
            EntityModel<RouteDTO> model = EntityModel.of(dto);
            model.add(WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder.methodOn(RoutesController.class).getRouteById(dto.getId(), null))
                    .withSelfRel());
            return model;
        });

        // The POST affordance is created from the createRoute method
        Link createLink = linkTo(methodOn(RoutesController.class).getRoutes(null, null))
                .withRel("create")
                .andAffordance(afford(methodOn(RoutesController.class)
                        .createRoute(new RouteRequestDTO(null, null, null, null, null, null, null))))
                .withTitle("Create a new route with CreateRouteRequestDTO")
                .withType(HttpMethod.POST.name());

        routesModel.add(createLink);

        return ResponseEntity.ok(routesModel);
    }

    /**
     * Retrieves a route by its ID.
     *
     * <p>
     * Optionally embeds related resources in the response.
     * </p>
     *
     * @param id the ID of the route to retrieve
     * @param embed optional query parameter to embed related resources.
     *              <ul>
     *                  <li><b>buses</b>: embeds the assigned buses for the route</li>
     *              </ul>
     *              Other values are ignored.
     * @return a {@link ResponseEntity} containing the HAL+JSON representation of the route,
     *         or 404 Not Found if the route does not exist.
     *
     * <p>
     * The HAL+JSON response includes links for self, edit, delete, and routes.
     * If <code>embed=buses</code> is specified, the assigned buses are embedded in the response.
     * </p>
     */
    @GetMapping(path = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<RepresentationModel<?>> getRouteById(
            @PathVariable Long id,
            @RequestParam(required = false) String embed) {
        Route route = routeService.getRouteById(id);
        if (route == null) {
            return ResponseEntity.notFound().build();
        }
        RouteDTO dto = routeMapper.toDTO(route);

        HalModelBuilder model = HalModelBuilder.halModelOf(dto);
        // EntityModel<RouteDTO> model = EntityModel.of(dto);
        model.link(linkTo(WebMvcLinkBuilder.methodOn(RoutesController.class).getRouteById(id, null))
                .withSelfRel());

        model.link(linkTo(methodOn(RoutesController.class).updateRoute(id, null))
                .withRel("edit")
                .withType(HttpMethod.PUT.name())
                .withTitle("Edit this route with RouteRequestDTO"));
        model.link(linkTo(methodOn(RoutesController.class).deleteRoute(id))
                .withRel("delete")
                .withType(HttpMethod.DELETE.name())
                .withTitle("Delete this route"));
        model.link(linkTo(WebMvcLinkBuilder.methodOn(RoutesController.class).getRoutes(null, null))
                .withRel("routes"));

        if ("buses".equals(embed)) {
            route.getAssignedBuses().forEach(bus -> {
                model.embed(EntityModel.of(bus)
                        .add(linkTo(methodOn(BusController.class).getBusById(bus.getId())).withSelfRel()));
            });
        }

        return ResponseEntity.ok(model.build());
    }

    // Create Route
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Route>> createRoute(@RequestBody RouteRequestDTO dto) {

        Route createdRoute = routeService.createRoute(routeMapper.toEntity(dto));
        EntityModel<Route> model = EntityModel.of(createdRoute);
        model.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(RoutesController.class).getRouteById(createdRoute.getId(), null))
                .withSelfRel());
        return ResponseEntity.created(model.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(model);
    }

    // Update Route

    @PutMapping(path = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<RouteDTO>> updateRoute(@PathVariable Long id, @RequestBody RouteRequestDTO dto) {
        Route updatedRoute = routeService.updateRoute(id, routeMapper.toEntity(dto));
        if (updatedRoute == null) {
            return ResponseEntity.notFound().build();
        }

        RouteDTO routeDTO = routeMapper.toDTO(updatedRoute);

        Link selfLink = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(RoutesController.class).getRouteById(id, null))
                .withSelfRel();

        return ResponseEntity.ok(EntityModel.of(routeDTO, selfLink));
    }

    // Delete Route
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable Long id) {
        Optional<Route> existingRoute = routeService.deleteRoute(id);
        if (existingRoute.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

}
