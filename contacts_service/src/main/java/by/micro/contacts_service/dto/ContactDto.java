package by.micro.contacts_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContactDto {
    private String phoneCode;
    private String phoneNumber;
}

