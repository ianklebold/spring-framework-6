package jedi.followmypath.webapp.config.resttemplate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.client.RestTemplateBuilderConfigurer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.util.DefaultUriBuilderFactory;

//Con COmponentScan y el filter le indicamos a Spring cuales componentes debe de ignorar y no llevarlo a contexto.
@Configuration
@ComponentScan(excludeFilters = @ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE,value = RestTemplateBuilderConfig.class))
public class RestTemplateBuilderConfig {

    @Value("${rest.template.rootUrl}")
    String rootUrl;

    @Value("${spring.security.user.name}")
    String username;

    @Value("${spring.security.user.password}")
    String password;

    @Bean
    RestTemplateBuilder restTemplateBuilder(RestTemplateBuilderConfigurer configurer){
        //Con esto reemplazamos la configuracion por default de Spring. Muy util ya que la URI base suele cambiar de seguido
        //Con esta configuracion ya no es necesaria poner "http://localhost:8080" en las consultas

        return  configurer.configure(new RestTemplateBuilder())
                .basicAuthentication(username,password) //Con esto configuramos que el builder tenga la autenticacion
                .uriTemplateHandler(new DefaultUriBuilderFactory(rootUrl));
    }

}
