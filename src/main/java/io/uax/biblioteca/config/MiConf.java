package io.uax.biblioteca.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MiConf implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/bibliotecarios").setViewName("forward:/");
        registry.addViewController("/lectors").setViewName("forward:/");
        registry.addViewController("/admins").setViewName("forward:/");
    }
}
