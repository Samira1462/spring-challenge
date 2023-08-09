/*
package com.codechallenge.employeeapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.HashSet;

@Configuration
public class SpringFoxConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.codechallenge.employeeapi"))
                .paths(PathSelectors.any())
                .build()
                .produces(new HashSet<String>(Arrays.asList("application/json")))
                .consumes(new HashSet<String>(Arrays.asList("application/json")))
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("YEmployee Restful API")
                .description("API documentation for your Spring Boot application")
                .version("1.0")
                .build();
    }


}*/
