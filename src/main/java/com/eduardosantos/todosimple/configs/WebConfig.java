package com.eduardosantos.todosimple.configs;

import javax.validation.constraints.NotNull;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


// Classe de configuração para permitir que o front-end acesse o back-end
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    
    
    // Serve para permitir que o front-end acesse o back-end
    @NotNull
    public void addCorsMappings(CorsRegistry registry) {
        // Configuração que permite que qualquer origem acesse o back-end
        registry.addMapping("/**");
    }
    
}
