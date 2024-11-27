package by.micro.contacts_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contacts", schema = "public")
@IdClass(ContactId.class)
public class ContactEntity {
    @Id
    @Column(name = "cv_id", nullable = false)
    private Integer cvId;

    @Id
    @Column(name = "phone_code_id", nullable = false)
    private Integer phoneCodeId;

    @Column(name = "phone_number", nullable = false, length = 25)
    private String phoneNumber;
}