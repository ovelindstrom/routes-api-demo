package se.codecadence.routes.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import se.codecadence.routes.dto.DestinationDTO;
import se.codecadence.routes.entities.Destination;
import se.codecadence.routes.mapper.DestinationMapper;
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
@RequestMapping({"/api/v1/destinations", "/api/v1/destinations/"})
public class DestinationController {

    private final DestinationService destinationService;
    private final DestinationMapper destinationMapper;

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<DestinationDTO>>> getDestinations(Pageable pageable,
            PagedResourcesAssembler<DestinationDTO> assembler) {


        Page<Destination> destinations = destinationService.getDestinations(pageable);
        Page<DestinationDTO> destinationDTOs = destinations.map(destinationMapper::toDTO);
        PagedModel<EntityModel<DestinationDTO>> destinationPage = assembler.toModel(destinationDTOs, dto -> {
                EntityModel<DestinationDTO> model = EntityModel.of(dto);
                model.add(org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
                        .linkTo(org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn(DestinationController.class)
                                .getDestinationById(dto.id()))
                        .withSelfRel());
                return model;
            });
        return ResponseEntity.ok(destinationPage);
    }

    @GetMapping({"/{id}", "/{id}/"})
    public ResponseEntity<EntityModel<DestinationDTO>> getDestinationById(@PathVariable Long id) {
        Destination destination = destinationService.getDestinationById(id);
        if (destination == null) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<DestinationDTO> model = EntityModel.of(destinationMapper.toDTO(destination));

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
