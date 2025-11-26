package Microservicio.de.Administracion.del.Sistema.service;

import Microservicio.de.Administracion.del.Sistema.model.Usuario;
import Microservicio.de.Administracion.del.Sistema.repository.IUsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {
    private final IUsuarioRepository UsuarioRepo;

    public AdminService(IUsuarioRepository usuarioRepo) {
        UsuarioRepo = usuarioRepo;
    }

    public Usuario cambiarPermisos(Long id_admin, Usuario admin, Long id_usuario, Integer rol) {
        String str;

        Optional<Usuario> usuario_admin = UsuarioRepo.findById(id_admin);
        Usuario u;

        if (admin.getEmail().equals(usuario_admin.get().getEmail())
                && admin.getPassword().equals(usuario_admin.get().getPassword())
                && usuario_admin.get().getRol().equals(0)){
                Usuario usuario_a_modificar = UsuarioRepo.findById(id_usuario).get();
                usuario_a_modificar.setRol(rol);
                UsuarioRepo.save(usuario_a_modificar);

                u = usuario_a_modificar;

        }else{
            u= new Usuario();
        }
        return u;
    }

}
