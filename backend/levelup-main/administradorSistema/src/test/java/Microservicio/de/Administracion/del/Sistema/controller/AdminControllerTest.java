package Microservicio.de.Administracion.del.Sistema.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import Microservicio.de.Administracion.del.Sistema.assembler.UsuarioModelAssembler;
import Microservicio.de.Administracion.del.Sistema.model.Usuario;
import Microservicio.de.Administracion.del.Sistema.service.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService adminService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UsuarioModelAssembler assembler;

    private Usuario admin;

    @BeforeEach
    void setUp() {
        admin = new Usuario();
        admin.setId_usuario(1);
        admin.setEmail("mack.will@yahoo.com");
        admin.setPassword("51gh79ve80t3a");
        admin.setRol(0);
    }

    @Test
    public void testCambiarPermisos() throws Exception {
        Long idAdmin = 1L;
        Long idUsuario = 5L;
        Integer nuevoRol = 3;

        // Ejecutar petición
        mockMvc.perform(put("/api/v2/admin/put")
                        .param("id_admin", "1")
                        .param("id_usuario", "5")
                        .param("rol", "3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(admin)))
                .andExpect(status().isOk());

    }
}
