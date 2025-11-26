package Microservicio.de.Administracion.del.Sistema.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API DigiPymes360 Administracion del Sistema")
                        .version("2.0")
                        .description("Documentación de la API para el sistema de administración del sistema."));
    }
}