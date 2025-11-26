
package Microservicio.de.Administracion.del.Sistema.assembler;
//*

import Microservicio.de.Administracion.del.Sistema.controller.SoporteController;
import Microservicio.de.Administracion.del.Sistema.model.Soporte;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SoporteModelAssembler implements RepresentationModelAssembler<Soporte, EntityModel<Soporte>> {

    @Override
    public EntityModel<Soporte> toModel(Soporte soporte) {
        return EntityModel.of(soporte,
                linkTo(methodOn(SoporteController.class).getTicketPorId(soporte.getId_soporte().longValue())).withSelfRel(),
                linkTo(methodOn(SoporteController.class).getTickets()).withRel("soportes"));
    }
}
//*/