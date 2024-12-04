package by.micro.contacts_service.service;


import by.micro.contacts_service.dto.ContactDto;
import by.micro.contacts_service.entity.ContactEntity;
import by.micro.contacts_service.repository.ContactsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactsService {

    private final ContactsRepository contactsRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ContactsServiceFeignClient contactsServiceFeignClient;

    @Transactional
    public ContactDto getContacts(Integer phoneCodeId) {
        ContactEntity contactEntity = contactsRepository.findByPhoneCodeId(phoneCodeId);
        log.debug("Received request for phone code with ID: {}", phoneCodeId);
        // String code = restTemplate.getForObject("http://PHONE-CODE-SERVICE/api/phone/code/" + contactEntity.getPhoneCodeId(), String.class);
        try {
            // Логируем начало запроса
            log.debug("Making Feign call to getCodeInfo with phoneCodeId: {}", phoneCodeId);
            String code = contactsServiceFeignClient.getCodeInfo(contactEntity.getPhoneCodeId()).getBody();
            log.debug("Received code: {}", code);
            return new ContactDto(code, contactEntity.getPhoneNumber());
        } catch (Exception e) {
            // Логируем ошибку, если она возникла
            log.debug("Error during Feign call for phoneCodeId {}: {}", phoneCodeId, e.getMessage());
            throw e;  // пробрасываем исключение дальше
        }
    }

    @Transactional
    public void saveContact(Integer cvId,
                            String phoneNumber,
                            String code,
                            Integer countryId
    ) {
        Integer phoneCodeId = contactsServiceFeignClient.saveCode(code, countryId).getBody();
        String url = "http://PHONE-CODE-SERVICE/api/phone/code/save?code=" + code + "&countryId=" + countryId;
//        ResponseEntity<Integer> response = restTemplate.exchange(
//                url,
//                HttpMethod.POST,
//                null,
//                Integer.class
//        );
//        Integer phoneCodeId = response.getBody();
        contactsRepository.save(new ContactEntity(cvId, phoneCodeId, phoneNumber));
    }
}
