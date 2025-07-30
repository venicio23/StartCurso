package com.venitech.startcurso.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "StartCurso API",
        version = "1.0.0",
        description = "API REST para Sistema de Cadastro de Cursos - Permite criação, visualização e inscrição em cursos",
        contact = @Contact(
            name = "Venicio",
            email = "venicio@startcurso.com",
            url = "https://github.com/venicio23/StartCurso"
        ),
        license = @License(
            name = "MIT License",
            url = "https://opensource.org/licenses/MIT"
        )
    ),
    servers = {
        @Server(url = "http://localhost:8080", description = "Servidor de Desenvolvimento"),
        @Server(url = "https://startcurso-api.herokuapp.com", description = "Servidor de Produção")
    }
)
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer",
    description = "JWT token para autenticação. Formato: Bearer {token}"
)
public class OpenApiConfig {
}
