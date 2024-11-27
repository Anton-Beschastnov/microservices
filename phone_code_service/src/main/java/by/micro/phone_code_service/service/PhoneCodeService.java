package by.micro.phone_code_service.service;

import by.micro.phone_code_service.entity.PhoneCodeEntity;
import by.micro.phone_code_service.repository.PhoneCodeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhoneCodeService {

    private final PhoneCodeRepository phoneCodeRepository;

    @Transactional
    public String getCode(Integer phoneCodeId) {
        return phoneCodeRepository.findById(phoneCodeId)
                                  .map(PhoneCodeEntity::getCode)
                                  .orElse("+7");
    }
@Transactional
    public Integer saveCode(String code,
                            Integer countryId) {
        PhoneCodeEntity phoneCodeEntity = new PhoneCodeEntity();
        phoneCodeEntity.setCode(code);
        phoneCodeEntity.setCountryId(countryId);
        PhoneCodeEntity rezPhoneCodeEntity = phoneCodeRepository.save(phoneCodeEntity);
        return rezPhoneCodeEntity.getId();
    }
}
