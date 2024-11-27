package by.micro.phone_code_service.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class ContactDto {
    private String code;
    private Integer countryId;
}

