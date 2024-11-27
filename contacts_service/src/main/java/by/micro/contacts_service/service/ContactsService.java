package by.micro.contacts_service.service;


import by.micro.contacts_service.dto.ContactDto;
import by.micro.contacts_service.entity.ContactEntity;
import by.micro.contacts_service.repository.ContactsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactsService {

    private final ContactsRepository contactsRepository;
    @Autowired
    private  RestTemplate restTemplate;

    public ContactDto getContacts(Integer phoneCodeId) {
        ContactEntity contactEntity = contactsRepository.findByPhoneCodeId(phoneCodeId);
      String code =  restTemplate.getForObject("http://PHONE-CODE-SERVICE/api/phone/code/" + contactEntity.getPhoneCodeId(), String.class);
        return new ContactDto(code, contactEntity.getPhoneNumber());
    }


//    public void saveContact(ContactDto contactDto) {
//        Contact contact = new Contact();
//        contact.setCvId(contactDto.getCvId());
//        contact.setPhoneCodeId(fetchPhoneCodeId(contactDto.getPhoneCode()));
//        contact.setPhoneNumber(contactDto.getPhoneNumber());
//        contactsRepository.save(contact);
//    }
//
//    private Map<Long, String> fetchPhoneCodes() {
//        ResponseEntity<List<PhoneCodeDto>> response = restTemplate.exchange(
//                "http://phone-code-service/phone-codes",
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<PhoneCodeDto>>() {
//                }
//        );
//
//        return response.getBody().stream()
//                       .collect(Collectors.toMap(PhoneCodeDto::getId, PhoneCodeDto::getCode));
//    }
//
//    private Long fetchPhoneCodeId(String phoneCode) {
//        ResponseEntity<List<PhoneCodeDto>> response = restTemplate.exchange(
//                "http://phone-code-service/phone-codes",
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<PhoneCodeDto>>() {
//                }
//        );
//
//        return response.getBody().stream()
//                       .filter(dto -> dto.getCode().equals(phoneCode))
//                       .findFirst()
//                       .orElseThrow(() -> new IllegalArgumentException("Invalid phone code"))
//                       .getId();
//    }
}
