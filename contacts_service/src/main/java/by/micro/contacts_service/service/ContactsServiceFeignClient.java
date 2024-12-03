package by.micro.contacts_service.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "PHONE-CODE-SERVICE")
public interface ContactsServiceFeignClient {
    @GetMapping("/api/phone/code/{phoneCodeId}")
    ResponseEntity<String> getCodeInfo(@PathVariable Integer phoneCodeId);

    @PostMapping("/api/phone/code/save")
    ResponseEntity<Integer> saveCode(@RequestParam String code,
                                     @RequestParam Integer countryId);
}
