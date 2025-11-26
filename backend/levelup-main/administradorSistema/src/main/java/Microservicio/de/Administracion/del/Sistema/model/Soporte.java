package Microservicio.de.Administracion.del.Sistema.model;
import java.util.Date;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Soporte")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Soporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_soporte;

    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_usuario")
    private Cliente id_cliente;

    @Column
    private String mensaje;

    @Column
    private String estado;

    @Column
    private Date fecha;

    public Integer getId_soporte() {
        return id_soporte;
    }

    public void setId_soporte(Integer id_soporte) {
        this.id_soporte = id_soporte;
    }

    public Cliente getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Cliente id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Soporte{" +
                "id_soporte=" + id_soporte +
                ", id_cliente=" + id_cliente +
                ", mensaje='" + mensaje + '\'' +
                ", estado='" + estado + '\'' +
                ", fecha=" + fecha +
                '}';
    }
}
