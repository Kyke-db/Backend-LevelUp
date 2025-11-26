package Microservicio.de.Administracion.del.Sistema.controller;

import Microservicio.de.Administracion.del.Sistema.assembler.UsuarioModelAssembler;
import Microservicio.de.Administracion.del.Sistema.model.Usuario;
import Microservicio.de.Administracion.del.Sistema.service.AdminService;
import Microservicio.de.Administracion.del.Sistema.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService adminService;

    @Autowired
    private ObjectMapper objectMapper;

    private Usuario admin,usuario;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private UsuarioModelAssembler assembler;
    @BeforeEach
    void setUp() {
        admin = new Usuario();
        admin.setId_usuario(1);
        admin.setEmail("mack.will@yahoo.com");
        admin.setPassword("51gh79ve80t3a");
        admin.setRol(0);

        usuario = new Usuario();
        usuario.setId_usuario(2);
        usuario.setEmail("pat.braun@gmail.com");
        usuario.setPassword("5q7a36zk6698cge");
        usuario.setRol(1);

    }

    @Test
    public void testAddUsuario() throws Exception {
        when(usuarioService.crearUsuario(any(Usuario.class), anyString(), anyString()))
                .thenReturn(usuario);

        when(assembler.toModel(any(Usuario.class)))
                .thenReturn(EntityModel.of(usuario));
        // Ejecutar petición
        mockMvc.perform(post("/api/v2/user/add")
                        .param("direccion", "calle 123")
                        .param("telefono", "12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(admin)))
                .andExpect(status().isCreated());

    }

    @Test
    public void testGetUsuarios() throws Exception {
        when(usuarioService.findAll()).thenReturn(List.of(new Usuario()));
        when(assembler.toModel(any(Usuario.class)))
                .thenReturn(EntityModel.of(usuario));
        // Ejecutar petición
        mockMvc.perform(get("/api/v2/user/get"))
                .andExpect(status().isOk());

    }

    @Test
    public void testGetById() throws Exception {
        when(usuarioService.obtenerPorId(1L)).thenReturn(usuario);
        when(assembler.toModel(any(Usuario.class)))
                .thenReturn(EntityModel.of(usuario));
        // Ejecutar petición
        mockMvc.perform(get("/api/v2/user/getbyid")
                .param("id","1")
                )
                .andExpect(status().isOk());

    }

    @Test
    public void testModificarUsuario() throws Exception {
        //when(usuarioService.actualizar(2L,admin,"pass123","pat.braun@gmail.com","Direccion 1234","56999887766")).thenReturn(usuarioDTO);//Se comenta si se ejecuta en VSCode
        when(assembler.toModel(any(Usuario.class)))
                .thenReturn(EntityModel.of(usuario));
        // Ejecutar petición
        mockMvc.perform(put("/api/v2/user/put")
                        .param("id","2")
                        .param("nuevo_email","pat.braun@gmail.com")
                        .param("nueva_password","pass123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario))
                )
                .andExpect(status().isOk());

    }

    @Test
    public void testModificarUsuarioActivarDesactivar() throws Exception {
        when(usuarioService.actualizarActivarDesactivar(2L,1L,admin,false)).thenReturn(usuario);
        when(assembler.toModel(any(Usuario.class)))
                .thenReturn(EntityModel.of(usuario));
        // Ejecutar petición
        mockMvc.perform(put("/api/v2/user/deactivate")
                        .param("id","2")
                        .param("id_admin","1")
                        .param("activar","0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(admin))
                )
                .andExpect(status().isOk());

    }

    @Test
    public void testEliminarUsuario() throws Exception {

        mockMvc.perform(delete("/api/v2/user/del")
                        .param("id","2")
                        .param("id_admin","1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(admin))
                )
                .andExpect(status().isNoContent());

    }

    @Test
    public void testEliminarUsuarioVoluntariamente() throws Exception {
        mockMvc.perform(delete("/api/v2/user/delself")
                        .param("id","2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario))
                )
                .andExpect(status().isNoContent());

    }
}
