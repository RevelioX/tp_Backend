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
                //Expongo todos los endpoint de estaciones
                .route(p -> p
                        .path("/api/estaciones")
                        .uri("http://localhost:7070"))
                //Expongo solo la ruta de alquileres, Tarfias no es necesario.
                .route(p -> p
                        .path("/api/alquileres")
                        .uri("http://localhost:7071"))
                .build();   //Resuelve el de la estación con menor distancia
    }

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws
            Exception {
        http.authorizeExchange(exchanges -> exchanges

                //CLIENTE Y ADMINSITRADORES pueden consultar las estaciones
                .pathMatchers(HttpMethod.GET,"/api/estaciones/**")
                    .hasAnyRole("CLIENTES","ADMINISTRADORES")

                //ADMINISTRADORES pueden crear nuevas estaciones
                .pathMatchers(HttpMethod.POST, "/api/estaciones/**")
                    .hasRole("ADMINISTRADORES")

                //CLIENTE Y ADMINSITRADORES pueden inciar y finalizar alquileres
                .pathMatchers("api/alquileres/iniciarAlquiler", "/api/alquileres/finalizarAlquiler")
                    .hasAnyRole("CLIENTES","ADMINISTRADORES")

                //ADMINISTRADORES pueden consultar(get) los alquileres
                .pathMatchers(HttpMethod.GET,"/api/alquileres/**")
                    .hasRole("ADMINISTRADORES")

                //El resto de peticiones tienen que estar autenticados
                .anyExchange()
                .authenticated()

        ).oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
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
                new
                        ReactiveJwtGrantedAuthoritiesConverterAdapter(grantedAuthoritiesConverter));
        // También se puede cambiar el claim que corresponde al nombre que luego se utilizará en el objeto
        // Authorization
        // jwtAuthenticationConverter.setPrincipalClaimName("user_name");
        return jwtAuthenticationConverter;
    }


}

