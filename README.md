# Sping Framework 6 & Spring boot 3

## Temario

- Build a Spring Boot Web App
- Use Spring for Dependency Injection
- Create RESTful Web Services with Spring MVC
- Create RESTful Web Services with Spring Webflux
- Create RESTful Web Services with Spring Webflux.fn
- Learn Best Practices using Project Lombok with Spring
- Create MapStruct Mappers as Spring Components
- Spring MockMVC with Mockito and JUnit 5
- Spring Data JPA
- Spring Data MongoDB
- Spring Data R2DBC (Reactive)
- Spring RestTemplate
- Spring WebClient
- Spring WebTestClient
- Spring Security HTTP Basic Authentication
- Spring Security OAuth2 Authentication w/ JWT
- Spring Authorization Server
- Spring WebMVC OAuth2 Resource Server
- Spring WebFlux OAuth2 Resource Server
- Spring Cloud Gateway
- Spring Boot Maven Plugin
- Spring Boot Gradle Plugin
- Use Java Bean Validation with Spring
- Spring Boot Auto-Configuration with MySQL
- Use Spring Boot and Flyway for Database Migrations
- Hibernate Database Relationship Mapping with Spring Data JPA

## Rama 1 - Primer aplicacion en Spring boot 3

En esta seccion se presenta las capas de la aplicacion. El MVC

### CAPA DE MODELO

Es aquella capa de clases que representan conceptos de la realidad, del dominio. Estos luego son mapeados en la capa de datos (base de datos).
Estas clases contienen datos (atributos) y responsabilidades.

![image](https://user-images.githubusercontent.com/56406481/216707467-98c487ec-c2d7-4acd-8c59-2d1737acaa87.png)


### CAPA DE VISTA

Puede ser representada de diferentes formas, el concepto de capa de vista. Es aquella la cual hace o envia consultas a la aplicacion, esta consulta es interceptada
por el dispatcher servlet y este redirecciona a un controlador que puede dar respuesta a esta consulta. La vista recibe la respuesta a la consulta la cual tendra
determinado formato (String, JSON, XML, etc) definido por el controlador.

### CAPA DE CONTROLADOR
Recibe la peticion, ejecuta la logica para dar respuesta a la consulta dependiendo de la operacion GET, PUT, POST, DELETE, ETC. Tiene una logica minima,
dejando la responsabilidad de logica del negocio a las clases de servicio.

![image](https://user-images.githubusercontent.com/56406481/216708828-23c19b62-83d4-4b45-9284-c5f480c29ea5.png)






















