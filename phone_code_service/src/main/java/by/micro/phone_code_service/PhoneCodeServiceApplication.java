package by.micro.phone_code_service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PhoneCodeServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PhoneCodeServiceApplication.class, args);
    }

}
