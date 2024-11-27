package by.micro.phone_code_service.api;
import by.micro.phone_code_service.dto.ContactDto;
import by.micro.phone_code_service.service.PhoneCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/phone/code")
@RequiredArgsConstructor
public class PhoneCodeApiController {
    private final PhoneCodeService phoneCodeService;

    @GetMapping("/{phoneCodeId}")
    public ResponseEntity<String> getCode(@PathVariable Integer phoneCodeId) {
        return ResponseEntity.ok(phoneCodeService.getCode(phoneCodeId));
    }

//    @PostMapping
//    public ResponseEntity<Void> saveContacts(@RequestBody ContactDto contactDto) {
//        phoneCodeService.saveContact(contactDto);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }
}
