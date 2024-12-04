package by.micro.contacts_service.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "PHONE-CODE-SERVICE")
public interface ContactsServiceFeignClient {
    @GetMapping("/api/phone/code/{phoneCodeId}")
    @Retry(name = "phone_code_service", fallbackMethod = "getCodeInfoFallback")
    @CircuitBreaker(name = "phone_code_service", fallbackMethod = "getCodeInfoFallback")
    ResponseEntity<String> getCodeInfo(@PathVariable Integer phoneCodeId);

    default ResponseEntity<String> getCodeInfoFallback(Integer phoneCodeId, Throwable exception) {
        System.out.println("phoneCodeId: " + phoneCodeId);
        System.out.println("exception: " + exception.getMessage());
        return ResponseEntity.ok("+12345");
    }
    @PostMapping("/api/phone/code/save")
    ResponseEntity<Integer> saveCode(@RequestParam String code,
                                     @RequestParam Integer countryId);
}
