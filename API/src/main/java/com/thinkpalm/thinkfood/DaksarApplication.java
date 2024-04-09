package com.thinkpalm.thinkfood;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "THINKFOOD", version = "1.0", description = "food delivery spring boot application"))
@SecurityScheme(name = "thinkfood", scheme = "bearer", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class DaksarApplication {

    public static void main(String[] args) {
        SpringApplication.run(DaksarApplication.class, args);
    }

}
