package by.micro.phone_code_service.repository;

import by.micro.phone_code_service.entity.PhoneCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneCodeRepository extends JpaRepository<PhoneCodeEntity, Integer> {
}
