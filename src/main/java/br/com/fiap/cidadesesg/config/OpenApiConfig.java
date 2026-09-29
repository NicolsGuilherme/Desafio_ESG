package br.com.fiap.cidadesesg.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${app.environment:development}")
    private String environment;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Cidades ESG Inteligentes - Plataforma de Monitoramento Urbano")
                .version("1.0.0")
                .description("API corporativa para monitoramento de telemetria ambiental, inventario de emissoes de CO2 e gestao de projetos de sustentabilidade urbana.\n\n" +
                             "**Integrantes do Grupo (Fase 6 - DevOps):**\n" +
                             "- Luan Chaves - RM562814\n" +
                             "- Matheus Nicacio - RM564257\n" +
                             "- Pietro - RM564024\n" +
                             "- Nicolas Guilherme - RM561466\n\n" +
                             "**Ambiente Atual:** " + environment.toUpperCase())
                .contact(new Contact()
                    .name("FIAP DevOps Team - Grupo 35")
                    .email("contato@cidadesesg.fiap.com.br"))
                .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0.html")))
            .servers(List.of(
                new Server().url("http://localhost:8080").description("Ambiente Local (Desenvolvimento)"),
                new Server().url("http://staging.cidadesesg.fiap.com.br:8081").description("Ambiente de Staging (Homologacao)"),
                new Server().url("https://api.cidadesesg.fiap.com.br").description("Ambiente de Producao")
            ));
    }
}
