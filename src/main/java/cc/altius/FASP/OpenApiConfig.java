package cc.altius.FASP;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private final String appName;
    private final String appVersion;
    private final String appDescription;
    
    public OpenApiConfig(
        @Value("${info.app.name}") String appName,
        @Value("${info.app.version}") String appVersion,
        @Value("${info.app.description}") String appDescription) {
        this.appName = appName;
        this.appVersion = appVersion;
        this.appDescription = appDescription;
    }
    
    @Bean
    public OpenAPI faspOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description(
                                            "Enter the JWT token in the format: Bearer <token>")))

                .info(new Info()
                        .title(appName)
                        .description(appDescription)
                        .version(appVersion)
                        .license(new License()
                                .name("Apache License Version 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}
