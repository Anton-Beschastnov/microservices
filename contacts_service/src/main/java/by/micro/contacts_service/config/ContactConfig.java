package by.micro.contacts_service.config;

import feign.Logger;
import feign.codec.ErrorDecoder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ContactConfig {
    @Bean
    @LoadBalanced
    public RestTemplate template() {
        return new RestTemplate();
    }

    @Bean
    public Logger.Level getFeignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public ErrorDecoder getFeignErrorDecoder() {
        return new FeignErrorDecoder();
    }
}
