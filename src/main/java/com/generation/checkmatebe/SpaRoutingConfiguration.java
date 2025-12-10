package com.generation.checkmatebe;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

@Configuration
public class SpaRoutingConfiguration implements WebMvcConfigurer {

    /**
     * Gestisce il routing per le Single Page Application (SPA) come Angular.
     * Reindirizza tutte le richieste non API e non-file a index.html.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // Serve le risorse statiche (JS, CSS, immagini)
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        Resource requestedResource = location.createRelative(resourcePath);

                        // Se la risorsa esiste, la restituisce (es. main.js, styles.css)
                        // Altrimenti, restituisce index.html (per le rotte Angular come /profilo)
                        return requestedResource.exists() && requestedResource.isReadable()
                                ? requestedResource
                                : new ClassPathResource("/static/index.html");
                    }
                });
    }
}