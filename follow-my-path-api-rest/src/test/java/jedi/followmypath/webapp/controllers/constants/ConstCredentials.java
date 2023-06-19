package jedi.followmypath.webapp.controllers.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

import java.time.Instant;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;


public class ConstCredentials {

    private static final String SCOPE = "scope";

    public static final SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor jwtRequestPostProcessor =
            jwt().jwt(
                    jwt -> {
                        jwt.claims(claims -> {
                            claims.put(SCOPE,"message-read");
                            claims.put(SCOPE,"message-write");
                        })
                                .subject("messaging-client")
                                .notBefore(Instant.now().minusSeconds(5L));
                        /*
                        El método notBefore de la clase Jwt en OAuth2 es utilizado para establecer la fecha y
                        hora antes de la cual el token JWT no debe ser considerado válido.
                        El tiempo not-before es una medida de seguridad que se utiliza para proteger
                        contra posibles ataques de repetición. Si un token JWT se emite con un tiempo not-before,
                        cualquier servidor o aplicación que procese el token debe asegurarse de que el tiempo actual
                        sea posterior al tiempo not-before antes de considerar el token como válido.
                        Por ejemplo, si se establece un tiempo not-before de 10 minutos en el futuro,
                        cualquier servidor o aplicación que procese el token debe esperar al menos 10 minutos antes de
                        considerar el token como válido. Esto ayuda a prevenir ataques de repetición en los que un
                        atacante intenta reutilizar un token anterior para obtener acceso no autorizado.
                        En resumen, el método notBefore de la clase Jwt en OAuth2 se utiliza para establecer un
                        tiempo not-before en un token JWT, lo que ayuda a proteger contra posibles ataques de repetición.
                         */
                    }
            );

}
