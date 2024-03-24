package esprit.pi.demo.Configuration;
import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI().info(infoAPI());
    }

    public Info infoAPI() {
        return new Info().title("SpringDoc-Demo")
                .description("TP étude de cas")
                .contact(contactAPI());
    }


    public Contact contactAPI() {
        return new Contact().name("Equipe ASI II")
                .email("chahnez.sardouk@esprit.tn")
                .url("https://www.linkedin.com/in/**********/");
    }

    @Bean
    public GroupedOpenApi CreditApi() {
        return GroupedOpenApi.builder()
                .group("Only credit Management API")
                .pathsToMatch("/Credit/**")
                .pathsToExclude("**")
                .build();
    }
    @Bean
    public GroupedOpenApi GarantApi() {
        return GroupedOpenApi.builder()
                .group("Only Garant Management API")
                .pathsToMatch("/Garant/**")
                .pathsToExclude("**")
                .build();
    }
    @Bean
    public GroupedOpenApi MonthlyPaymentApi() {
        return GroupedOpenApi.builder()
                .group("Only Monthly Payment Management API")
                .pathsToMatch("/MonthlyPayment/**")
                .pathsToExclude("**")
                .build();
    }
    @Bean
    public GroupedOpenApi PackCRApi() {
        return GroupedOpenApi.builder()
                .group("Only Pack Credit Management API")
                .pathsToMatch("/PackCR/**")
                .pathsToExclude("**")
                .build();
    }
    @Bean
    public GroupedOpenApi TransactionCApi() {
        return GroupedOpenApi.builder()
                .group("Only Transaction Management API")
                .pathsToMatch("/TransactionCrédit/**")
                .pathsToExclude("**")
                .build();
    }

    @Bean
    public GroupedOpenApi UserApi() {
        return GroupedOpenApi.builder()
                .group("Only User Management API")
                .pathsToMatch("/user/**")
                .pathsToExclude("**")
                .build();
    }

    @Bean
    public GroupedOpenApi PortefeuilleApi() {
        return GroupedOpenApi.builder()
                .group("Only Portefeuille Management API")
                .pathsToMatch("/Portefeuille/**")
                .pathsToExclude("**")
                .build();
    }



}
