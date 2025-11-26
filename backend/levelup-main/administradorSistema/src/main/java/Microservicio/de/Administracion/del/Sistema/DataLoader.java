//Descomentar si es necesario rellenar una base de datos.


package Microservicio.de.Administracion.del.Sistema;
/*
import Microservicio.de.Administracion.del.Sistema.model.Cliente;
import Microservicio.de.Administracion.del.Sistema.model.Soporte;
import Microservicio.de.Administracion.del.Sistema.model.Usuario;
import Microservicio.de.Administracion.del.Sistema.repository.IClienteRepository;
import Microservicio.de.Administracion.del.Sistema.repository.ISoporteRepository;
import Microservicio.de.Administracion.del.Sistema.repository.IUsuarioRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private IClienteRepository clienteRepository;
    @Autowired
    private ISoporteRepository soporteRepository;
    @Autowired
    private IUsuarioRepository usuarioRepository;

    List<String> estado = new ArrayList<>();


    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();

        //Listado de datos de estado (para luego ser seleccionados al azar)
        estado.add("Resuelto");
        estado.add("No resuelto");
        estado.add("En revisión");
        estado.add("Escalado");
        estado.add("Pendiente");
        estado.add("Cerrado");
        estado.add("En espera del cliente");

        //Generar usuarios,clientes y ticket de soporte
        for (int i = 0;i<50;i++){
            Usuario usuario = new Usuario();

            if (i%5==0) {
                usuario.setActivo(false);
            }else{
                usuario.setActivo(true);
            }

            usuario.setNombre(faker.name().fullName());
            usuario.setEmail(faker.internet().emailAddress());
            usuario.setRol(i%4);
            usuario.setPassword(faker.internet().password(10,15));

            usuario = usuarioRepository.save(usuario);

            if (i%4==3) {//Cuando el rol es cliente
                Cliente cliente = new Cliente();
                cliente.setDireccion(faker.address().city());
                cliente.setTelefono(faker.phoneNumber().phoneNumber());
                cliente.setId_usuario(usuario);
                cliente = clienteRepository.save(cliente);

                Soporte soporte = new Soporte();
                soporte.setId_cliente(cliente);
                soporte.setEstado(estado.get(new Random().nextInt(estado.size())));

                LocalDate desde = LocalDate.of(2020, 1, 1);
                LocalDate hasta = LocalDate.of(2025, 6, 1);

                long desdeEpoch = desde.toEpochDay();
                long hastaEpoch = hasta.toEpochDay();

                long fechaRandom = random.nextLong(desdeEpoch, hastaEpoch);
                LocalDate fecha = LocalDate.ofEpochDay(fechaRandom);

                Date fechaGenerada = Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant());
                soporte.setFecha(fechaGenerada);
                soporte.setMensaje("Mensaje "+random.nextInt(0,999999));//Genera un mensaje con numero al azar
                soporteRepository.save(soporte);
            }

        }

    }
}
//*/