package by.micro.contacts_service.repository;

import by.micro.contacts_service.entity.ContactEntity;
import by.micro.contacts_service.entity.ContactId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContactsRepository extends JpaRepository<ContactEntity, ContactId> {
    @Query("select u from ContactEntity u where u.phoneCodeId =:phoneCodeId")
    ContactEntity findByPhoneCodeId(@Param("phoneCodeId") Integer phoneCodeId);

}
