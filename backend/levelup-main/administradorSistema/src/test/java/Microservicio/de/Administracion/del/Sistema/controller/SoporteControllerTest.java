package Microservicio.de.Administracion.del.Sistema.controller;

import Microservicio.de.Administracion.del.Sistema.assembler.SoporteModelAssembler;
import Microservicio.de.Administracion.del.Sistema.assembler.UsuarioModelAssembler;
import Microservicio.de.Administracion.del.Sistema.model.Cliente;
import Microservicio.de.Administracion.del.Sistema.model.Soporte;
import Microservicio.de.Administracion.del.Sistema.model.Usuario;
import Microservicio.de.Administracion.del.Sistema.service.AdminService;
import Microservicio.de.Administracion.del.Sistema.service.SoporteService;
import Microservicio.de.Administracion.del.Sistema.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SoporteController.class)
public class SoporteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService adminService;

    @Autowired
    private ObjectMapper objectMapper;

    private Usuario usuario;
    private Soporte ticketSoporte;
    private Cliente cliente;
    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private SoporteService soporteService;

    @MockBean
    private SoporteModelAssembler assembler;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId_usuario(4);
        usuario.setEmail("eduardo.white@gmail.com");
        usuario.setPassword("89iz0brue1ppc");
        usuario.setRol(3);

        cliente = new Cliente();
        cliente.setDireccion("Rowebury");
        cliente.setTelefono("(730) 676-5073");
        cliente.setId_usuario(usuario);

        ticketSoporte = new Soporte();
        ticketSoporte.setId_cliente(cliente);
        ticketSoporte.setEstado("En revisión");

        LocalDate localDate = LocalDate.of(2022, 12, 18);

        Date fecha = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        ticketSoporte.setFecha(fecha);
        ticketSoporte.setMensaje("Mensaje 912855");//Genera un mensaje con numero al azar
    }

    @Test
    void testListSupport() throws Exception {
        mockMvc.perform(get("/api/v2/support/supervisar")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetTicket() throws Exception {
        mockMvc.perform(get("/api/v2/support/get")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id","1"))
                .andExpect(status().isOk());
    }

    @Test
    void testAddTicket() throws Exception {
        when(soporteService.crearTicket(anyLong(), any(Soporte.class)))
                .thenReturn(ticketSoporte);

        mockMvc.perform(post("/api/v2/support/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id_cliente","1")
                        .content(objectMapper.writeValueAsString(ticketSoporte)))
                .andExpect(status().isOk());

    }

    @Test
    void testSolve() throws Exception {
        mockMvc.perform(delete("/api/v2/support/solve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id_cliente","1"))
                .andExpect(status().isNoContent());

    }
}
