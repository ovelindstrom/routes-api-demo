package se.codecadence.routes.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import se.codecadence.routes.entities.Bus;
import se.codecadence.routes.service.BusService;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;

@RestController
@RequestMapping({"/api/v1/buses", "/api/v1/buses/"})
@AllArgsConstructor
public class BusController {

    private BusService busService;

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<Bus>>> getBuses(Pageable pageable,
            PagedResourcesAssembler<Bus> assembler) {
        Page<Bus> busPage = busService.getBuses(pageable);
        return ResponseEntity.ok(assembler.toModel(busPage));
    }

    @GetMapping({"/{id}", "/{id}/"})
    public ResponseEntity<EntityModel<Bus>> getBusById(@PathVariable Long id) {
        Bus bus = busService.getBusById(id);
        if (bus == null) {
            return ResponseEntity.notFound().build();
        }
        EntityModel<Bus> model = EntityModel.of(bus);
        model.add(
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BusController.class).getBusById(id)).withSelfRel());
        model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BusController.class).getBuses(null, null))
                .withRel("buses"));
        return ResponseEntity.ok(model);
    }

}
