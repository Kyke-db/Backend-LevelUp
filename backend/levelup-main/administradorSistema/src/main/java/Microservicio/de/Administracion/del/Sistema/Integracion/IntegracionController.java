package Microservicio.de.Administracion.del.Sistema.Integracion;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class IntegracionController {

    private final RestTemplate restTemplate;

    public IntegracionController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping(value = "/listarFactura", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> obtenerTextoCrudo() {
        String url = "http://127.0.0.1:8081/facturas";
        String texto = restTemplate.getForObject(url, String.class);

        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(texto);
    }
}
