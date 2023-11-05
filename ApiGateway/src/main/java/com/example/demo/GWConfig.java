package com.example.demo;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtGrantedAuthoritiesConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;




@Configuration
@EnableWebFluxSecurity
public class GWConfig {
    @Bean
    public RouteLocator configurarRutas(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/api/estaciones")
                        .and()
                        .method(HttpMethod.GET)
                        .or()
                        .method(HttpMethod.POST)//Para solo exponer el Metodo GET y no todos los demas
                        .uri("http://localhost:7070")
                ) //Resuelve el GET y POST de Estaciones.
                .route(p -> p
                        .path("/api/estaciones/cercana")
                        .uri("http://localhost:7070"))
                .build();   //Resuelve el de la estación con menor distancia
    }//todo Faltaria hacer la wea segura, pero los endpoints son estos :P

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception {
        http.authorizeExchange(exchanges -> exchanges

                        // Esta ruta puede ser accedida por cualquiera, sin autorización, solo GET
                        .pathMatchers(HttpMethod.GET, "/api/entradas/**")
                        .permitAll()

                        // Esta ruta requiere rol "KEMPES_ADMIN" para las solicitudes POST
                        .pathMatchers(HttpMethod.POST, "/api/entradas/**")
                        .hasRole("ADMINISTRADOR")

                        .pathMatchers(HttpMethod.GET, "/api/estaciones/cercana")
                        .permitAll()

                        // Cualquier otra petición...
                        .anyExchange()
                        .authenticated()

                ).oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .csrf(csrf -> csrf.disable());
        return http.build();
    }

    @Bean
    public ReactiveJwtAuthenticationConverter jwtAuthenticationConverter() {
        var jwtAuthenticationConverter = new ReactiveJwtAuthenticationConverter();
        var grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

        // Se especifica el nombre del claim a analizar
        grantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");
        // Se agrega este prefijo en la conversión por una convención de Spring
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        // Se asocia el conversor de Authorities al Bean que convierte el token JWT a un objeto Authorization
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(
                new ReactiveJwtGrantedAuthoritiesConverterAdapter(grantedAuthoritiesConverter));
        // También se puede cambiar el claim que corresponde al nombre que luego se utilizará en el objeto
        // Authorization
        // jwtAuthenticationConverter.setPrincipalClaimName("user_name");

        return jwtAuthenticationConverter;

    }
}

