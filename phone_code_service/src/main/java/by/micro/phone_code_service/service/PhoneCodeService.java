package by.micro.phone_code_service.service;

import by.micro.phone_code_service.dto.ContactDto;
import by.micro.phone_code_service.entity.PhoneCodeEntity;
import by.micro.phone_code_service.repository.PhoneCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PhoneCodeService {

    private final PhoneCodeRepository phoneCodeRepository;


    public String getCode(Integer phoneCodeId) {
        return phoneCodeRepository.findById(phoneCodeId)
                                  .map(PhoneCodeEntity::getCode)
                                  .orElse("+7");
    }

//    public void saveContact(ContactDto contactDto) {
//        Contact contact = new Contact();
//        contact.setCvId(contactDto.getCvId());
//        contact.setPhoneCodeId(fetchPhoneCodeId(contactDto.getPhoneCode()));
//        contact.setPhoneNumber(contactDto.getPhoneNumber());
//        phoneCodeRepository.save(contact);
//    }
//
//    private Map<Long, String> fetchPhoneCodes() {
//        ResponseEntity<List<PhoneCodeDto>> response = restTemplate.exchange(
//                "http://phone-code-service/phone-codes",
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<PhoneCodeDto>>() {}
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
//                new ParameterizedTypeReference<List<PhoneCodeDto>>() {}
//        );
//
//        return response.getBody().stream()
//                       .filter(dto -> dto.getCode().equals(phoneCode))
//                       .findFirst()
//                       .orElseThrow(() -> new IllegalArgumentException("Invalid phone code"))
//                       .getId();
//    }
}
