package by.micro.contacts_service.api;

import by.micro.contacts_service.dto.ContactDto;
import by.micro.contacts_service.service.ContactsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact/")
@RequiredArgsConstructor
public class ContactApiController {
    private final ContactsService contactsService;

    @GetMapping("{phoneCodeId}")
    public ResponseEntity<ContactDto> getContacts(@PathVariable Integer phoneCodeId) {
        return ResponseEntity.ok(contactsService.getContacts(phoneCodeId));
    }

    @PostMapping("save")
    public ResponseEntity<Void> saveContacts(@RequestParam Integer cvId,
                                             @RequestParam String phoneNumber,
                                             @RequestParam String code,
                                             @RequestParam Integer countryId) {
        contactsService.saveContact(cvId, phoneNumber, code, countryId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
