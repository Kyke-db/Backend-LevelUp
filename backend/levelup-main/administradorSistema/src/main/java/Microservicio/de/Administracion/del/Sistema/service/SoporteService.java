package Microservicio.de.Administracion.del.Sistema.service;

import Microservicio.de.Administracion.del.Sistema.model.Cliente;
import Microservicio.de.Administracion.del.Sistema.model.Soporte;
import Microservicio.de.Administracion.del.Sistema.model.Usuario;
import Microservicio.de.Administracion.del.Sistema.repository.IClienteRepository;
import Microservicio.de.Administracion.del.Sistema.repository.ISoporteRepository;
import Microservicio.de.Administracion.del.Sistema.repository.IUsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SoporteService {
    private final ISoporteRepository SoporteRepo;
    private final IClienteRepository clienteRepo;

    public SoporteService(ISoporteRepository soporteRepo, IClienteRepository clienteRepo) {
        SoporteRepo = soporteRepo;
        this.clienteRepo = clienteRepo;
    }

    public List<Soporte> getTickets() {
        return SoporteRepo.findAll();
    }

    public Soporte crearTicket(Long id_cliente, Soporte ticket) {
        Cliente cliente = clienteRepo.findById(id_cliente).get();

        ticket.setId_cliente(cliente);
        SoporteRepo.save(ticket);
        return ticket;
    }


    public String solucionarTicket( Long id) {
        SoporteRepo.deleteById(id);
        return "Ticket eliminado";
    }

    public Soporte obtenerTicket(Long id){
        return SoporteRepo.findById(id).get();
    }
}
