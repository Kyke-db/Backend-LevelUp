package Microservicio.de.Administracion.del.Sistema.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
@Entity
@Table(name = "Usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_usuario;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(unique  = false, length = 100, nullable = false)
    private String nombre;

    @NotBlank(message = "La clave es obligatoria")
    @Column(unique  = false, length = 100, nullable = false)
    private String password;

    @NotBlank(message = "El email es obligatorio")
    @Column(unique  = true, length = 100, nullable = false)
    private String email;

    @Column
    private Integer rol;

    @Column
    private boolean activo;




    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public void setNombre(@NotBlank(message = "El nombre es obligatorio") String nombre) {
        this.nombre = nombre;
    }

    public void setPassword(@NotBlank(message = "La clave es obligatoria") String password) {
        this.password = password;
    }

    public void setEmail(@NotBlank(message = "El email es obligatorio") String email) {
        this.email = email;
    }

    public void setRol(Integer rol) {
        this.rol = rol;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public @NotBlank(message = "El nombre es obligatorio") String getNombre() {
        return nombre;
    }

    public @NotBlank(message = "La clave es obligatoria") String getPassword() {
        return password;
    }

    public @NotBlank(message = "El email es obligatorio") String getEmail() {
        return email;
    }

    public Integer getRol() {
        return rol;
    }

    public boolean isActivo() {
        return activo;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id_usuario=" + id_usuario +
                ", nombre='" + nombre + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", rol=" + rol +
                ", activo=" + activo +
                '}';
    }
}
