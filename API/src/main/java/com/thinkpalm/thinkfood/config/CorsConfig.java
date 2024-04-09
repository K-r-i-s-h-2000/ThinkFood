package com.thinkpalm.thinkfood.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

//@Configuration
//public class CorsConfig {
//    @Bean
//    public CorsFilter corsFilter() {
//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        final CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.setAllowedOriginPatterns(Collections.singletonList("*"));
//        config.setAllowedHeaders(Collections.singletonList("*"));
//        config.setAllowedMethods(Arrays.stream(HttpMethod.values()).map(HttpMethod::name).collect(Collectors.toList()));
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }
//}

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/think-food/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("OPTIONS", "GET", "POST","PATCH","DELETE","PUT")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
