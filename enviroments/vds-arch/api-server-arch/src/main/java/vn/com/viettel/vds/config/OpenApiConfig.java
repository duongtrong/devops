package vn.com.viettel.vds.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Viettel Digital Service")
                        .description("Chuyển dịch công nghệ")
                        .contact(new Contact()
                                .name("CDCN")
                                .url("https://transformation.digital.vn/"))
                        .version("1.0.0"));
    }
}
