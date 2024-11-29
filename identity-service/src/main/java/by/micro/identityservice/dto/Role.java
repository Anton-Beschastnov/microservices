package by.micro.identityservice.dto;

import org.springframework.security.core.GrantedAuthority;

/**
 * метод getAuthority будет вызываться спринг секьюрити модулем,
 * чтобы узнать какие роли есть у пользователя
 */
public enum Role  implements GrantedAuthority {
    ADMIN, USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
