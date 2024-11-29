package by.micro.identityservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static by.micro.identityservice.dto.Role.ADMIN;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(CsrfConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("api/contact", "/v3/api-docs/", "swagger-ui/").permitAll()//все доступные страницы без входа в систему
                    .requestMatchers("/admin/**").hasRole(ADMIN.getAuthority())
                    .anyRequest().authenticated());
        return http.build();
    }
}
