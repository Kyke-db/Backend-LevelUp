
package Microservicio.de.Administracion.del.Sistema.assembler;
//*

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import Microservicio.de.Administracion.del.Sistema.controller.UsuarioController;
import Microservicio.de.Administracion.del.Sistema.model.Usuario;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

    @Override
    public EntityModel<Usuario> toModel(Usuario usuario) {
        return EntityModel.of(usuario,
                linkTo(methodOn(UsuarioController.class).getById(usuario.getId_usuario().longValue())).withSelfRel(),
                linkTo(methodOn(UsuarioController.class).getUsuarios()).withRel("usuarios"));
    }
}
//*/