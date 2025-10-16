package sunuguide.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI sunuGuideOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SunuGuide API – Backend Spring Boot")
                        .version("1.0.0")
                        .description("SunuGuide - API pour calcul d'itinéraires et chatbot")
                        .contact(new Contact().name("Équipe SunuGuide").email("contact@sunuguide.sn").url("https://sunuguide.sn"))
                        .license(new License().name("MIT").url("https://opensource.org/licenses/MIT"))
                );
    }
}
