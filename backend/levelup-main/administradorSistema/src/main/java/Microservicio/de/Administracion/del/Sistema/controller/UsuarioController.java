package Microservicio.de.Administracion.del.Sistema.controller;

import Microservicio.de.Administracion.del.Sistema.DTO.UsuarioDTO;
import Microservicio.de.Administracion.del.Sistema.assembler.UsuarioModelAssembler;
import Microservicio.de.Administracion.del.Sistema.model.Usuario;
import Microservicio.de.Administracion.del.Sistema.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v2/user/")
@CrossOrigin(origins = "*")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioModelAssembler assembler;

    @PostMapping(value = "/add",produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Añade usuario.")
    public ResponseEntity<EntityModel<Usuario>> addUsuario(@RequestBody Usuario usuario, @RequestParam String direccion, @RequestParam String telefono) {

        Usuario usuarioGuardado = this.usuarioService.crearUsuario(usuario,direccion,telefono);
        return ResponseEntity
                .created(linkTo(methodOn(UsuarioController.class).getById(usuario.getId_usuario().longValue())).toUri())
                .body(assembler.toModel(usuarioGuardado));
    }

    @GetMapping(value = "/get",produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtiene usuarios.")
    public CollectionModel<EntityModel<Usuario>> getUsuarios() {
        //return usuarioService.findAll();

        List<EntityModel<Usuario>> carreras = usuarioService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(carreras,
                linkTo(methodOn(UsuarioController.class).getUsuarios()).withSelfRel());
    }

    @GetMapping(value = "/getbyid",produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "obtiene usuario por id.")
    public EntityModel<Usuario> getById(@RequestParam Long id) {

        Usuario u = this.usuarioService.obtenerPorId(id);
        return assembler.toModel(u);
    }

    @PutMapping(value = "/put",produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Modifica usuarios.")
    public UsuarioDTO modificarUsuario(@RequestParam Long id, @RequestBody Usuario usuario, @RequestParam String nueva_password, @RequestParam String nuevo_email, @RequestParam String direccion,@RequestParam String telefono) {
        UsuarioDTO usuarioRet= this.usuarioService.actualizar(id,usuario,nueva_password,nuevo_email,direccion,telefono);
        //return ResponseEntity.ok(assembler.toModel(usuarioRet));
        return usuarioRet;
    }

    @PutMapping(value = "/deactivate",produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Desactiva usuarios.")
    public ResponseEntity<EntityModel<Usuario>> modificarUsuarioActivarDesactivar(@RequestParam Long id,@RequestParam Long id_admin,@RequestBody Usuario usuario, @RequestParam boolean activar) {
        Usuario usuarioRet = this.usuarioService.actualizarActivarDesactivar(id,id_admin,usuario,activar);
        return ResponseEntity.ok(assembler.toModel(usuarioRet));
    }

    @DeleteMapping(value = "/del",produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Elimina usuarios.")
    public ResponseEntity<?> eliminarUsuario(@RequestParam Long id, @RequestParam Long id_admin, @RequestBody Usuario admin) {
        usuarioService.eliminarUsuario(id,id_admin, admin);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/delself",produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Elimina usuario cuando el usuario quiera eliminarse.")
    public ResponseEntity<?> eliminarUsuarioVoluntariamente(@RequestParam Long id, @RequestBody Usuario usuario) {
        usuarioService.eliminarVoluntariamente(id,usuario);
        return ResponseEntity.noContent().build();

    }

    @GetMapping(value = "/loginDP360")
    @Operation(summary = "Log in")
    public boolean loginDP360(@RequestParam String email, @RequestParam String password) {
        return this.usuarioService.loginDP360(email,password);
    }

    @GetMapping(value = "/loginINFO")
    @Operation(summary = "Log in info")
    public ResponseEntity<UsuarioDTO> loginINFO(@RequestParam String email, @RequestParam String password) {
        return this.usuarioService.obtenerPorLogueo(email, password);
    }


}
