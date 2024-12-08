package by.micro.mvc_oauth2_client_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, ClientRegistrationRepository clientRegistrationRepository) throws Exception {
        return http.authorizeHttpRequests(authorise ->
                authorise
                        .requestMatchers("/static/**", "/templates/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                   .csrf(AbstractHttpConfigurer::disable)
                   .exceptionHandling(authorise -> authorise.accessDeniedPage("/access-denied"))
                   .oauth2Login(outh2Login->
                           outh2Login.userInfoEndpoint(userInfoEndpoint->
                                   userInfoEndpoint.userAuthoritiesMapper(userAuthoritiesMapper())
                           )
                   )
                   .logout(logout -> logout
                           .logoutSuccessHandler(oidcLogoutSuccessHandler(clientRegistrationRepository))
                           .invalidateHttpSession(true)
                           .clearAuthentication(true)
                           .deleteCookies("JSESSIONID")
                   ).build();
    }

    @Bean
    @SuppressWarnings("unchecked")
    public GrantedAuthoritiesMapper userAuthoritiesMapper() {
        return (authorities) -> {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

            authorities.forEach(authority -> {
                if (authority instanceof OidcUserAuthority oidcUserAuthority) {
                    OidcUserInfo userInfo = oidcUserAuthority.getUserInfo();
                    Map<String, Object> realmAccess = userInfo.getClaim("realm_access");
                    Collection<String> realmRoles;
                    if (realmAccess != null
                            && (realmRoles = (Collection<String>) realmAccess.get("roles")) != null) {
                        realmRoles
                                .forEach(role -> mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role)));
                    }
                }
            });
            return mappedAuthorities;
        };
    }
}