package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GWConfig {
    @Bean
    public RouteLocator configurarRutas(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/api/estaciones")
                        .uri("http://localhost:7070")
                ) //Resuelve el GET y POST de Estaciones.
                .route(p -> p
                    .path( "/api/estaciones/cercana")
                    .uri("http://localhost:7070"))
                .build();   //Resuelve el de la estación con menor distancia
    }//todo Faltaria hacer la wea segura, pero los endpoints son estos :P
}

