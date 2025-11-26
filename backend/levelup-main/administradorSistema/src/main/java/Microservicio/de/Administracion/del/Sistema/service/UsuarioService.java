package Microservicio.de.Administracion.del.Sistema.service;

import Microservicio.de.Administracion.del.Sistema.DTO.UsuarioDTO;
import org.springframework.dao.DataIntegrityViolationException;
import Microservicio.de.Administracion.del.Sistema.model.Cliente;
import Microservicio.de.Administracion.del.Sistema.model.Usuario;
import Microservicio.de.Administracion.del.Sistema.repository.IClienteRepository;
import Microservicio.de.Administracion.del.Sistema.repository.IUsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final IUsuarioRepository usuarioRepo;
    private final IClienteRepository clienteRepo;

    public UsuarioService(IUsuarioRepository usuarioRepo, IClienteRepository clienteRepo) {
        this.usuarioRepo = usuarioRepo;
        this.clienteRepo = clienteRepo;
    }

    public List<Usuario> findAll() {

        List<Usuario> usuarios =  usuarioRepo.findAll();

        for (int i = 0;i < usuarios.size();i++){
            Usuario obtenerUsuario = usuarios.get(i);
            obtenerUsuario.setEmail("");
            obtenerUsuario.setPassword("");
            usuarios.set(i,obtenerUsuario);
        }

        return usuarios;

    }


        public ResponseEntity<UsuarioDTO> obtenerPorLogueo(String email, String password) {
        try {
            // Buscar usuario por email y password
            Optional<Usuario> usuarioOpt = usuarioRepo.findByEmailAndPassword(email, password);

            if (usuarioOpt.isEmpty()) {
                System.out.println("Usuario no encontrado para: " + email);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            Usuario usuario = usuarioOpt.get();

            // Buscar cliente asociado al usuario (aquí asumo que comparten el mismo ID)
            Optional<Cliente> clienteOpt = clienteRepo.findById(usuario.getId_usuario().longValue());

            if (clienteOpt.isEmpty()) {
                System.out.println("Cliente no encontrado para ID de usuario: " + usuario.getId_usuario());
                // Creamos un DTO solo con datos del usuario (sin dirección ni teléfono)
                UsuarioDTO dto = new UsuarioDTO();
                dto.setId_usuario(usuario.getId_usuario());
                dto.setRol(usuario.getRol());
                dto.setNombre(usuario.getNombre());
                dto.setEmail(usuario.getEmail());
                dto.setPassword(usuario.getPassword());
                dto.setActivo(usuario.isActivo());
                dto.setDireccion("No registrada");
                dto.setTelefono("No registrado");

                return ResponseEntity.ok(dto);
            }

            Cliente cliente = clienteOpt.get();

            // Si encontramos ambos, armamos el DTO completo
            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setId_usuario(usuario.getId_usuario());
            usuarioDTO.setRol(usuario.getRol());
            usuarioDTO.setNombre(usuario.getNombre());
            usuarioDTO.setEmail(usuario.getEmail());
            usuarioDTO.setPassword(usuario.getPassword());
            usuarioDTO.setActivo(usuario.isActivo());
            usuarioDTO.setDireccion(cliente.getDireccion());
            usuarioDTO.setTelefono(cliente.getTelefono());

            System.out.println("Usuario logueado correctamente: " + usuario.getEmail());
            return ResponseEntity.ok(usuarioDTO);

        } catch (Exception e) {
            System.err.println("Error al obtener usuario: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    public Usuario obtenerPorId(Long id) {
        String ret = "Permiso denegado.";
        Usuario u = usuarioRepo.findById(id).get();
        u.setPassword("");
        u.setEmail("");
        //ret = u.toString();

        return u;

    }

    public Usuario crearUsuario(Usuario u, String direccion, String telefono) {
            if (!usuarioRepo.findAll().isEmpty()) {
                u.setRol(3); // cliente por defecto (Después se puede modificar)
            } else {
                u.setRol(0); // admin
            }
            u.setId_usuario(null);
            // Guarda primero el usuario (asigna ID)
            usuarioRepo.save(u);

            if (u.getRol() == 3) {
                Cliente cliente = new Cliente();
                cliente.setId_usuario(u); // Usuario ya tiene ID
                cliente.setDireccion(direccion);
                cliente.setTelefono(telefono);
                clienteRepo.save(cliente);
            }

            return u;
    }

    public String eliminarVoluntariamente(Long id, Usuario usuario) {
        String ret = "No se puede eliminar el usuario.";

        Optional<Usuario> usuario_get = usuarioRepo.findById(id);



        if (usuario_get.isPresent()
                && usuario.getPassword().equals(usuario_get.get().getPassword())
                && usuario.getEmail().equals(usuario_get.get().getEmail()) &&
                usuario_get.get().getId_usuario().equals(usuario.getId_usuario())) {

            Usuario usuarioEncontrado = usuario_get.get();

            List<Cliente> clientes = clienteRepo.findAll();
            for (Cliente c : clientes) {
                if (c.getId_usuario().getId_usuario().equals(usuarioEncontrado.getId_usuario())) {
                    clienteRepo.delete(c);
                    break;
                }
            }

            usuarioRepo.deleteById(id);
            ret = "Usuario eliminado.";
        }

        return ret;
    }

    public boolean loginDP360(String email, String password) {

        List<Usuario> usuarios =  usuarioRepo.findAll();

        boolean ret = false;
        for (int i = 0;i < usuarios.size();i++){

            Usuario obtenerUsuario = usuarios.get(i);

            if (obtenerUsuario.getEmail().equals(email) && obtenerUsuario.getPassword().equals(password)) {
                ret = true;
            }
        }

        return ret;

    }

    public String eliminarUsuario(Long id,Long id_admin, Usuario admin) {
        String ret = "No se puede eliminar el usuario.";

        Optional<Usuario> check_admin = usuarioRepo.findById(id_admin);

        if (check_admin.isPresent()
                && admin.getPassword().equals(check_admin.get().getPassword())
                && admin.getEmail().equals(check_admin.get().getEmail())
                && check_admin.get().getRol()==0) {

            Usuario usuarioEncontrado = usuarioRepo.findById(id).get();

            List<Cliente> clientes = clienteRepo.findAll();
            for (Cliente c : clientes) {
                if (c.getId_usuario().getId_usuario().equals(usuarioEncontrado.getId_usuario())) {
                    clienteRepo.delete(c);
                    break;
                }
            }

            usuarioRepo.deleteById(id);
            ret = "Usuario eliminado.";
        }

        return ret;
    }





    public UsuarioDTO actualizar(Long id, Usuario datos, String nueva_password,String nuevo_email,String nueva_direccion,String nuevo_telefono) {
        String str = "No se pudo actualizar.";
        UsuarioDTO udto = new UsuarioDTO();
        Usuario usuario = usuarioRepo.findById(id).get();
        if (usuario.getPassword().equals(datos.getPassword())
                && usuario.getEmail().equals(datos.getEmail())){
            //&& usuario.isActivo()) {

            usuario.setPassword(nueva_password);
            usuario.setEmail(nuevo_email);
            usuario.setNombre(datos.getNombre());
            usuarioRepo.save(usuario);

            List<Cliente> lista_clientes = clienteRepo.findAll();
            for (int i = 0; i< lista_clientes.size();i++){
                if (lista_clientes.get(i).getId_usuario().getId_usuario().equals(usuario.getId_usuario())){
                    lista_clientes.get(i).setDireccion(nueva_direccion);

                    udto.setId_usuario(usuario.getId_usuario());
                    udto.setRol(usuario.getRol());
                    udto.setNombre(usuario.getNombre());
                    udto.setEmail(usuario.getEmail());
                    udto.setPassword(usuario.getPassword());
                    udto.setActivo(usuario.isActivo());
                    udto.setDireccion(nueva_direccion);
                    udto.setTelefono(nuevo_telefono);

                    lista_clientes.get(i).setDireccion(nueva_direccion);
                    lista_clientes.get(i).setTelefono(nuevo_telefono);

                    clienteRepo.save(lista_clientes.get(i));

                }
            }
            str = "Usuario actualizado.";
        }
        return udto;
    }

    public Usuario actualizarActivarDesactivar(Long id,Long id_admin, Usuario datos, boolean activar) {
        Usuario usuario = usuarioRepo.findById(id).get();
        Usuario admin = usuarioRepo.findById(id_admin).get();

        if (admin.getPassword().equals(datos.getPassword())
                && admin.getEmail().equals(datos.getEmail())
                && admin.getRol()==0) {
            usuario.setActivo(activar);
            usuarioRepo.save(usuario);
        }
        return usuario;
    }

}
