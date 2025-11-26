package Microservicio.de.Administracion.del.Sistema.service;

import Microservicio.de.Administracion.del.Sistema.model.Cliente;
import Microservicio.de.Administracion.del.Sistema.model.Soporte;
import Microservicio.de.Administracion.del.Sistema.model.Usuario;
import Microservicio.de.Administracion.del.Sistema.repository.IClienteRepository;
import Microservicio.de.Administracion.del.Sistema.repository.ISoporteRepository;
import Microservicio.de.Administracion.del.Sistema.repository.IUsuarioRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
public class SoporteServiceTest {
    @InjectMocks
    private SoporteService soporteService;

    Random random = new Random();
    List<String> estado = new ArrayList<>();

    @Mock
    private ISoporteRepository soporteRepository;

    @Mock
    private IClienteRepository clienteRepo;

    @Mock
    private IUsuarioRepository usuarioRepo;

    //Faker faker = new Faker();

    @Test
    public void testGetAllSoportes(){
        when(soporteRepository.findAll()).thenReturn(List.of(new Soporte()));
        List<Soporte> soportes = soporteService.getTickets();
        assertNotNull(soportes);
        assertNotEquals(0, soportes.size());
    }


    @Test
    public void testCrearTicket() {
        estado.clear();

        // Supón que el cliente tiene ID 20
        Long id_prueba = 20L;
        Integer id_prueba_int = id_prueba.intValue();

        // Usuario asociado al cliente
        Usuario usuario = new Usuario();
        usuario.setId_usuario(id_prueba_int);

        // Cliente con ID del usuario
        Cliente cliente = new Cliente();
        cliente.setId_cliente(usuario.getId_usuario());

        // Mock: cuando busque el usuario por ID de cliente, devuelve el usuario
        when(usuarioRepo.findById(id_prueba)).thenReturn(Optional.of(usuario));
        // Mock: cuando busque el cliente por ID de usuario, devuelve el cliente
        when(clienteRepo.findById(id_prueba)).thenReturn(Optional.of(cliente));

        // Estados
        estado.add("Resuelto");
        estado.add("No resuelto");
        estado.add("En revisión");
        estado.add("Escalado");
        estado.add("Pendiente");
        estado.add("Cerrado");
        estado.add("En espera del cliente");

        Soporte soporte = new Soporte();
        soporte.setId_cliente(cliente);
        soporte.setEstado("Test");

        LocalDate desde = LocalDate.of(2020, 1, 1);
        LocalDate hasta = LocalDate.of(2025, 6, 1);

        long desdeEpoch = desde.toEpochDay();
        long hastaEpoch = hasta.toEpochDay();
        long fechaRandom = random.nextLong(desdeEpoch, hastaEpoch);
        LocalDate fecha = LocalDate.ofEpochDay(fechaRandom);

        Date fechaGenerada = Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant());
        soporte.setFecha(fechaGenerada);
        soporte.setMensaje("Mensaje " + random.nextInt(0,999999));

        // Ejecución
        Soporte soporte_creado;
        soporte_creado = soporteService.crearTicket(id_prueba, soporte);

        assertNotNull(soporte_creado);
        assertEquals(soporte, soporte_creado);
    }

    @Test
    public void testSolucionarTicket(){
        Long id_prueba = 20L;
        Integer id_prueba_int = id_prueba.intValue();
        String eliminacion = soporteService.solucionarTicket(id_prueba);

        // Verifica que se llamó a deleteById correctamente
        verify(soporteRepository, times(1)).deleteById(id_prueba);

    }
}
