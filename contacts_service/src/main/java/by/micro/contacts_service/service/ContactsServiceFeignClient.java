package by.micro.contacts_service.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "PHONE-CODE-SERVICE")
public interface ContactsServiceFeignClient {
    @GetMapping("/api/phone/code/{phoneCodeId}")
    @CircuitBreaker(name = "phone_code_service", fallbackMethod = "getCodeInfoFallback")
    ResponseEntity<String> getCodeInfo(@PathVariable Integer phoneCodeId);

    default ResponseEntity<String> getCodeInfoFallback( Integer phoneCodeId, Throwable exception){
        System.out.println("phoneCodeId: " + phoneCodeId);
        System.out.println("exception: " + exception.getMessage());
        return ResponseEntity.ok("+7");
    }

    @PostMapping("/api/phone/code/save")
    ResponseEntity<Integer> saveCode(@RequestParam String code,
                                     @RequestParam Integer countryId);
}
