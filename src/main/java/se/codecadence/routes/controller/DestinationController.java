package se.codecadence.routes.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import se.codecadence.routes.entities.Destination;
import se.codecadence.routes.service.DestinationService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/destinations")
public class DestinationController {

    private final DestinationService destinationService;

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<Destination>>> getDestinations(Pageable pageable,
            PagedResourcesAssembler<Destination> assembler) {
        Page<Destination> destinationPage = destinationService.getDestinations(pageable);
        return ResponseEntity.ok(assembler.toModel(destinationPage));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Destination>> getDestinationById(@PathVariable Long id) {
        Destination destination = destinationService.getDestinationById(id);
        if (destination == null) {
            return ResponseEntity.notFound().build();
        }
        EntityModel<Destination> model = EntityModel.of(destination);

        model.add(org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
                .linkTo(org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn(DestinationController.class)
                        .getDestinationById(id))
                .withSelfRel());
        model.add(
                org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
                        .linkTo(org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
                                .methodOn(DestinationController.class).getDestinations(null, null))
                        .withRel("destinations"));

        return ResponseEntity.ok(model);
    }

}
