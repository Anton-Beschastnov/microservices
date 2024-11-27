package by.micro.contacts_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContactPhoneCodeDto {
    private String code;
    private Integer countryId;
}