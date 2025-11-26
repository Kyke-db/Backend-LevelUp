package Microservicio.de.Administracion.del.Sistema.repository;

import Microservicio.de.Administracion.del.Sistema.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClienteRepository extends JpaRepository<Cliente, Long> {
}
