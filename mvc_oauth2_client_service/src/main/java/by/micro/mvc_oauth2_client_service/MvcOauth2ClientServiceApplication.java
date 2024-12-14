package by.micro.mvc_oauth2_client_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MvcOauth2ClientServiceApplication {
    public static String name;

    public static void main(String[] args) {
        SpringApplication.run(MvcOauth2ClientServiceApplication.class, args);
    }

}
