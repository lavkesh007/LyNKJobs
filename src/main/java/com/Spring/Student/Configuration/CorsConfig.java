package com.Spring.Student.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://lynkjob.d1z9wprzw5dh5p.amplifyapp.com")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}