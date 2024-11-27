package by.micro.contacts_service.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ContactId implements Serializable {

    private Integer cvId;
    private Integer phoneCodeId;

    public ContactId() {
    }

    public ContactId(Integer cvId, Integer phoneCodeId) {
        this.cvId = cvId;
        this.phoneCodeId = phoneCodeId;
    }
}
