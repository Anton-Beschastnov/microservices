package by.micro.identityservice.config;

import by.micro.identityservice.repository.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * loadUserByUsername - dao аутентификация, секьюрите вызывает этот метод,
 * что проверить подлинность юзера, который пытается залогиниться
 * UserDetails содержит логин пароль и роли
 * В Spring Security, когда вы используете метод permitAll() для определённых путей,
 * это означает, что доступ к этим путям разрешён всем пользователям без необходимости аутентификации.
 * о есть Spring Security не будет пытаться проверить пользователя через механизм аутентификации
 * (например, через loadUserByUsername), потому что доступ к этим ресурсам не требует авторизации.
 *
 * Что происходит в других случаях?
 * Когда запрос требует аутентификацию:
 *
 * Например, если запрос направляется на защищённый ресурс (например, /admin/**),
 * который не разрешён для всех (он требует роли ADMIN или другого уровня доступа),
 * Spring Security будет пытаться аутентифицировать пользователя.
 * В процессе аутентификации Spring Security вызовет метод loadUserByUsername из вашего UserDetailsService,
 * чтобы найти пользователя по имени (или по какому-то другому идентификатору), загрузить его данные и
 * проверить его роль/права.
 * Когда запрос требует аутентификацию (но разрешён определённым ролям):
 *
 * Например, если вы указали:
 * .requestMatchers("/admin/**").hasRole("ADMIN")
 * Тогда запрос на /admin/** будет требовать аутентификацию,
 * и метод loadUserByUsername будет вызван для поиска пользователя.
 * Если пользователь найден и его роль соответствует ADMIN, доступ будет разрешён.
 * В противном случае, будет отказано в доступе.
 * Как это работает с JWT
 * В вашем случае, если вы используете JWT для аутентификации,
 * то запросы на пути, такие как /auth/token, /auth/validate или /auth/register,
 * скорее всего не требуют проверки пользователя через UserDetailsService,
 * поскольку эти эндпоинты обычно используются для получения токена или его проверки
 * (не для доступа к защищённым ресурсам).
 *
 * Для защищённых путей (например, /admin/**),
 * Spring Security будет ожидать наличие валидного токена
 * (например, в заголовке Authorization), и если токен присутствует,
 * Spring Security использует фильтры для извлечения информации из токена
 * (например, пользователя и его роли) и вызывает метод loadUserByUsername для аутентификации.
 *
 * Таким образом, метод loadUserByUsername не будет вызываться для путей,
 * которые явно указаны как доступные всем (permitAll()),
 * но будет вызван для защищённых путей, где требуется аутентификация.
 */

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserCredentialRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                         .map(CustomUserDetails::new)
                         .orElseThrow(() -> new UsernameNotFoundException("User not found with name : " + username));
    }
}