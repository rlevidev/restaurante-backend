package com.rlevi.restaurante_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("🍽️ Restaurante API")
                        .version("v1.0.0")
                        .description("""
                                API REST completa para gerenciamento de restaurantes.

                                ## Funcionalidades Principais
                                - 🔐 **Autenticação JWT**: Sistema seguro de login e registro
                                - 🍕 **Gerenciamento de Cardápio**: CRUD completo de alimentos
                                - 📦 **Sistema de Pedidos**: Controle de pedidos com status em tempo real
                                - 👤 **Perfil do Usuário**: Histórico de pedidos e informações pessoais

                                ## Autenticação
                                Para acessar endpoints protegidos, inclua o token JWT no header:
                                ```
                                Authorization: Bearer {seu-token-jwt}
                                ```
                                """)
                        .contact(new Contact()
                                .name("Equipe de Desenvolvimento")
                                .url("https://github.com/rlevidev/restaurante-backend"))
                        .license(new License()
                                .name("GPL-3.0")
                                .url("https://www.gnu.org/licenses/gpl-3.0.html")))
                .servers(java.util.List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Ambiente de Desenvolvimento")))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Token JWT obtido através do endpoint /auth/login")))
                .security(java.util.List.of(new SecurityRequirement().addList("bearerAuth")));
    }
}
