package Microservicio.de.Administracion.del.Sistema.service;

import Microservicio.de.Administracion.del.Sistema.model.Usuario;
import Microservicio.de.Administracion.del.Sistema.repository.IUsuarioRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AdminServiceTest {
    @Mock
    private IUsuarioRepository usuarioRepo;

    @InjectMocks
    private AdminService adminService;

    @Test
    public void testCambiarPermisos() {
        Long idAdmin = 1L;
        Long idUsuario = 5L; // usuario a modificar

        // Mock del admin
        Usuario admin = new Usuario();
        admin.setId_usuario(idAdmin.intValue());
        admin.setEmail("mack.will@yahoo.com");
        admin.setPassword("51gh79ve80t3a");
        admin.setRol(0);

        // Mock del usuario cuyo rol cambia
        Usuario usuario = new Usuario();
        usuario.setId_usuario(idUsuario.intValue());
        usuario.setEmail("otro.usuario@email.com");
        usuario.setPassword("abcdef123");
        usuario.setRol(1);

        Integer nuevoRol = 3;

        // Mock: buscar admin y buscar usuario a modificar
        when(usuarioRepo.findById(idAdmin)).thenReturn(Optional.of(admin));
        when(usuarioRepo.findById(idUsuario)).thenReturn(Optional.of(usuario));

        Usuario resultado = adminService.cambiarPermisos(idAdmin, admin, idUsuario, nuevoRol);

        // Assert
        assertNotNull(resultado);
        assertEquals(usuario, resultado);
        assertEquals(nuevoRol, usuario.getRol());

        // Verifica que se haya guardado el usuario
        verify(usuarioRepo).save(usuario);
    }



}
