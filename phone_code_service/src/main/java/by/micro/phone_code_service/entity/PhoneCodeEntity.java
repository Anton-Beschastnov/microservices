package by.micro.phone_code_service.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "phone_codes", schema = "public")
public class PhoneCodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Используем автоинкремент
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "code", nullable = false, length = 10)
    private String code;

    @Column(name = "country_id", nullable = false)
    private Integer countryId;
}
