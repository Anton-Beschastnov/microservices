package by.micro.gateway_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.List;
import java.util.stream.Stream;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain
    springSecurityFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange(auth -> auth
                .pathMatchers("/auth/token").permitAll()
                .pathMatchers("/api/contact/save").hasRole("ROLE_ADMIN")
                .anyExchange().authenticated())
            .oauth2ResourceServer(oauth2 -> oauth2
                    .jwt(jwt ->
                            jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));
        http.csrf(ServerHttpSecurity.CsrfSpec::disable);
        return http.build();
    }
    @Bean
    public ReactiveJwtAuthenticationConverterAdapter
    jwtAuthenticationConverter() {
        var jwtGrantedAuthoritiesConverter = new
                JwtGrantedAuthoritiesConverter();
        JwtAuthenticationConverter converter = new
                JwtAuthenticationConverter();
        converter.setPrincipalClaimName("preferred_username");
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            var authorities = jwtGrantedAuthoritiesConverter.convert(jwt);
            var roles = (List<String>)
                    jwt.getClaimAsMap("realm_access").get("roles");
            return Stream.concat(authorities.stream(),
                    roles.stream()
                         .map(role -> "ROLE_" + role)
                         .map(SimpleGrantedAuthority::new)
                         .map(GrantedAuthority.class::cast))
                         .toList();
        });
        return new ReactiveJwtAuthenticationConverterAdapter(converter);
    }
}
