package Microservicio.de.Administracion.del.Sistema.controller;

import Microservicio.de.Administracion.del.Sistema.assembler.SoporteModelAssembler;
import Microservicio.de.Administracion.del.Sistema.model.Soporte;
import Microservicio.de.Administracion.del.Sistema.service.SoporteService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v2/support/")
@CrossOrigin(origins = "*")
public class SoporteController {
    @Autowired
    private SoporteService soporteService;

    @Autowired
    private SoporteModelAssembler assembler;

    @GetMapping(value = "/supervisar",produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtiene tickets de soporte.")
    public CollectionModel<EntityModel<Soporte>> getTickets() {

        //return soporteService.getTickets();

        List<EntityModel<Soporte>> carreras = soporteService.getTickets().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(carreras,
                linkTo(methodOn(SoporteController.class).getTickets()).withSelfRel());
    }

    @GetMapping(value = "/get",produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtiene un ticket de soporte.")
    public EntityModel<Soporte> getTicketPorId(Long id) {
        Soporte s = soporteService.obtenerTicket(id);
        return assembler.toModel(s);

    }

    @PostMapping(value = "/add",produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Añade tickets de soporte.")
    public ResponseEntity<EntityModel<Soporte>> addTicket(@RequestParam Long id_cliente, @RequestBody Soporte ticket) {
        Soporte s = soporteService.crearTicket(id_cliente,ticket);
        return ResponseEntity
                .ok(assembler.toModel(s));
    }

    @DeleteMapping(value = "/solve",produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Elimina al solucionar el problema")
    public ResponseEntity<Object> solucionar(Long id_ticket) {
        soporteService.solucionarTicket(id_ticket);
        return ResponseEntity.noContent().build();
    }
}
