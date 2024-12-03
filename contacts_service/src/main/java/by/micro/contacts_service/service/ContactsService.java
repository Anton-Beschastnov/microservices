package by.micro.contacts_service.service;


import by.micro.contacts_service.dto.ContactDto;
import by.micro.contacts_service.entity.ContactEntity;
import by.micro.contacts_service.repository.ContactsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ContactsService {

    private final ContactsRepository contactsRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ContactsServiceFeignClient contactsServiceFeignClient;

    @Transactional
    public ContactDto getContacts(Integer phoneCodeId) {
        ContactEntity contactEntity = contactsRepository.findByPhoneCodeId(phoneCodeId);
        // String code = restTemplate.getForObject("http://PHONE-CODE-SERVICE/api/phone/code/" + contactEntity.getPhoneCodeId(), String.class);
        String code = contactsServiceFeignClient.getCodeInfo(contactEntity.getPhoneCodeId()).getBody();
        return new ContactDto(code, contactEntity.getPhoneNumber());
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
