package com.hdfclife.desk.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI hdfcLifeOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("HDFC Life Policy Desk API")
                        .version("1.0")
                        .description(
                                "REST API for managing HDFC Life policies and claims"
                        ));
    }
}