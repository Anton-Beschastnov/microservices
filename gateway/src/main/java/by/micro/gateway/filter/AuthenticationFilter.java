package by.micro.gateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    @Autowired
    private WebClient.Builder webClientBuilder;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
                return exchange.getResponse().setComplete();
            }

            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
                return exchange.getResponse().setComplete();
            }

            authHeader = authHeader.substring(7).trim();

            String url = "http://IDENTITY-SERVICE/auth/validate?token=" + authHeader;
            // Вызов удаленного сервиса через WebClient
            return webClientBuilder.build()
                                   .get()
                                   .uri(url)
                                   .retrieve()
                                   .toBodilessEntity()
                                   .flatMap(response -> {
                                       if (response.getStatusCode().is2xxSuccessful()) {
                                           return chain.filter(exchange); // Если успешно, продолжаем цепочку
                                       } else {
                                           exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                                           return exchange.getResponse().setComplete();
                                       }
                                   })
                                   .onErrorResume(error -> {
                                       exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                                       return exchange.getResponse().setComplete();
                                   });
        };
    }

    public static class Config {
        // В случае необходимости можно добавить настройки
    }
}
