package Microservicio.de.Administracion.del.Sistema.controller;

import Microservicio.de.Administracion.del.Sistema.assembler.UsuarioModelAssembler;
import Microservicio.de.Administracion.del.Sistema.model.Usuario;
import Microservicio.de.Administracion.del.Sistema.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/admin/")
@CrossOrigin(origins = "*")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private UsuarioModelAssembler assembler;

    @PutMapping(value = "/put",produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Modifica rol de usuario.")
    public ResponseEntity<EntityModel<Usuario>> modificarUsuario(
            @RequestParam("id_admin") Long idAdmin,
            @RequestBody Usuario usuario,
            @RequestParam("id_usuario") Long idUsuario,
            @RequestParam("rol") Integer nuevoRol
    ) {
        Usuario u = adminService.cambiarPermisos(idAdmin, usuario, idUsuario, nuevoRol);
        return ResponseEntity.ok(assembler.toModel(u));
    }

}
