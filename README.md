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

![image](https://user-images.githubusercontent.com/56406481/216797129-a6f57bb1-da6c-4bc3-8153-00dd1d960fa1.png)


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


## Rama 2 - Inyeccion de dependencias

![image](https://user-images.githubusercontent.com/56406481/216797157-1cf67a8c-3c7f-45d4-af21-e51ccf2d0ba9.png)

La inyeccion de dependencias es uno de los principios del SOLID el cual nos dice: 

1. Las clases de alto nivel no deberian depender de las clases de bajo nivel
2. Las abstracciones no deberian depender de los detalles, los detalles si deben depender de las abstracciones.

Debemos abstraer a las clases de la responsabilidad de instanciar los objetos de las clases con las que colabora. Debemos evitar ese fuerte acoplamiento y dependencias de las clases. Spring nos permite aplicar distintos mecanismos para aplicar la inyeccion de estas clases, evitando asi la fuerte dependencia. 

Las tres formas de gestionar la inyeccion de dependencias:

- Constructores
- Propiedades
- Setters



[Mas informacion de SOLID](https://github.com/ianklebold/solid-principies)

### PROPIEDADES

Esta es la peor forma. Consiste en instanciar la clase que va a colaborar con otras clases y esta asignarle en sus atributos los objetos a los cuales depende. Esta es la forma mas acoplada posible por lo que no se recomienda su uso, hay una fuerte dependencia entre las clases, la forma de instanciarlos podria cambiar por consecuente el codigo constantemente cambiara. 

```
        propertyInjectedController = new PropertyInjectedController();
        propertyInjectedController.greetingService = new GreetingServiceImpl(); //Instanciamos clase.

```


### METODO SET
El primer gran problema de este es que si no lo seteaste, saltara un Null Pointer Exception

Consiste en simplemente instanciar la clase, setearle las clases en las cuales este depende y luego usarlas.


### CONSTRUCTORES

Esta forma es la mas utilizada porque corregimos ese posible error de null pointer exception
Cuando deseamos intanciar el controlador nos pide por el constructor que le pasemos todas aquellos objetos
que este utiliza. Es el mas recomendado de usar.



Hoy por hoy contamos con la inversion de control, que es delegar la responsabilidad de ejecutar estos mecanismos de inyeccion de dependencia al framework
en este caso a Spring. Tenemos anotaciones como: 

- @Autowired

@Autowired, el cual indicamos al framework la inyeccion de una clase a un atributo

```
@Autowired
ClaseAInyectar claseAInyectar;
```

- @Qualifier

Una de las mejores practicas es declarar como privado al atributo de la clase que sera inyectada, a su vez estas clases deben de implementar una interfaz
Podemos tener varias clases que implementen dicha interfaz esto me permite reutilizar el nombrado de los metodos que utilizan dichas clases pero a su vez
utilizar la interfaz para la inyeccion de dependencias. 

En lugar de utilizar la clase para inyectar, haremos referencia a la interfaz, la interfaz es como un representante de todas las clases que la implementan. Esto nos permite hacer mas desacoplada las clases.


```
public interface InterfazRepresentante(){...}

@Service
public class ClaseServiceImpl implements ClaseService(){...}

@Service
public class ClaseServiceImpl2 implements ClaseService(){...}

@Service
public class ClaseServiceImpl3 implements ClaseService(){...}


```
@Service es la anotacion que le permite a spring saber que es una clase a inyectar y que cuando lo haga pasara al contexto de Spring como Bean.

Todas estas clases, implementan a la misma interfaz, esta misma interfaz representa a todas estas clases que la implementan.

Para la inyecion de dependencias entonces:

Al ser representante en el se puede inyectar cualquier servicio dependiendo de lo que se requiera.

```
private ClaseService claseService; // Representa a ClaseServiceImpl, ClaseServiceImpl2 , ClaseServiceImpl3

```
El problema es que ahora Spring Framework no sabe a quien instanciar. **@Qualifier** nos permite indicarle que Clase que implementa la interfaz tiene que inyectar en el atributo, solamente hay que indicarle el nombre de la clase. 

```
@Qualifier("claseServiceImpl")
private ClaseService claseService;

```

En constructor: 

```
public ClaseCualquiera(@Qualifier("claseServiceImpl") ClaseService claseService) {
        this.claseService = claseService;
    }
```

Spring framework hace uso de la refleccion, la refleccion es aquella en la cual me permite a partir del nombre de una clase obtenerla. Se convierte la clase en Bean pasando al contexto de Spring.

Cuando la clase no pertenece al contexto de Spring, utilizamos reflexion sino, toma el Bean del contexto de Spring (ApplicationContext).

Si no queremos usar el nombre de la clase, podemos a @Service de cada clase darle un alias. 


```

@Service("name1")
public class ClaseServiceImpl implements ClaseService(){...}

@Service("name2")
public class ClaseServiceImpl2 implements ClaseService(){...}

@Service("name3")
public class ClaseServiceImpl3 implements ClaseService(){...}


```

```
public ClaseCualquiera(@Qualifier("name1") ClaseService claseService) {
        this.claseService = claseService;
    }
```

- @Primary 

Podemos darle prioridad a las clases que van a inyectarse con la anotacion **@Primary** con el decimos que por default y como prioridad (Siempre que no se indique con @Qualifier) inyectemos la clase anotada con @Primary. 

```

@Service("name1")
public class ClaseServiceImpl implements ClaseService(){...}

@Primary
@Service("name2")
public class ClaseServiceImpl2 implements ClaseService(){...}

@Service("name3")
public class ClaseServiceImpl3 implements ClaseService(){...}


```

```
public ClaseCualquiera(ClaseService claseService) {
        this.claseService = claseService;  //Instanciamos a service impl 2 por @Primary
    }
```

Condiciones: Para poder darse la inyeccion de dependencias, debe anotarse tanto a la clase que le van a inyectar una clase para colaborar con ella como en la clase que sera inyectada que como minimo seran componentes del sistema, es decir con **@Component**. Esto le dice a Spring que la clase será un Bean del contexto y le permitira llevar a cabo la inyeccion de dependencia y la inversion de control.

@Service, @Controller, @RestController, entre otros, heredan de @Component.


## Introduccion a RestFul web services

El proceso de conversion de POJOs a Json o XML se llama Marshallings
El proceso de convertir Json o XML a POJOs se llama Unmarshallings

### HTTP PROTOCOLOS

Hasta hy en dia tenemos 3 versiones de Protocolos http


- HTTP V 1.1
Utiliza TLS/SSL de forma tradicional y es opcionalmente encriptada la informacion que se envia. 

- HTTP V 2.0
Utiliza TLS 1.2 o mas grande, viene siempre encriptada (por default) . Como mejoras tenemos de baja latencia y alto rendimiento.

- HTTP V 3.0
Utiliza QUIC NETWORK PROTOCOL, basado en HTTP V 2.0 y viene siempre encriptada (por default)

HTTP no es mas que otro protocolo de comunicacion, basados en metodos.

### METODOS HTTP

Los metodos son operaciones que se desean realizar sobre algun recurso existente en la aplicacion. Dependiendo del tipo de metodo se requeriran algunos datos que forman parte de la estructura de comunicacion de este protocolo. 

**Request Body**
Son los datos que son cargados y enviados a la aplicacion.

**Response Body**
Son los datos que seran proveidos ante determinada request o consulta.

Caracteristicas: 

Los metodos pueden caracterizarse por:

**SAFE O SEGURO**
Un metodo que es seguro es aquella que al ser ejecutada no hace ninguna modificacion a los datos guardados, no hay cambios en la aplicacion, no importa la cantidad de veces que sea ejecuta. 

**IDEMPOTENT O IDEMPOTENCIA**
Un metodo es idempotente cuando al ser ejecutado puede o no modificar los datos guardados, por ejemplo cuando se manda un METODO PUT y no se encunentra el recurso a actualizar tiene la opcion de no modificar los datos guardados. 

**CACHABLE**
Un metodo es cachable cuando al ejecutarlo, guarda en memoria cache el metodo y los resultados del mismo, de tal manera que al hacer otra vez la misma consulta el resultado ya no vuelve a ser calculado sino que es consultado en la memoria CACHE la cual es mas rapida. Con el pasar del tiempo, esa consulta y resultados en cache pueden ser eliminados (Ya que se considera que no es necesario ese recurso o no es esta siendo consultado) por lo que sera necesario en esos casos volver a calcularlos, guardarlo en memoria y devolver el resultado de la misma.

![Captura desde 2023-02-05 17-43-45](https://user-images.githubusercontent.com/56406481/216849510-5067a3c1-27b4-4be7-a290-2d7b00ade89f.png)


Por cada ejecucion de un metodo se tienen codigos de respuesta, que nos puede dar inidice del estado de como termino la operacion.

- 100 : Indica informacion enviada
- 200 : Peticion resuleta con exito
- 300 : Indica redireccionamiento. Por ejemplo que una peticion ya no existe en una direccion.
- 400 : Errores del lado del cliente
- 500 : Errores del lado del servidor, de la aplicacion.


Los mas comunes:

**La serie 200**

- 200 : OKay salio todo bien
- 201 : Creado
- 204 : Aceptado

**La serie 300**

- 301 : El recurso fue movido permanente

**La serie 400**

- 400 : Peticion erronea
- 401 : No autorizado
- 404 : No se encuentra el recurso solicitado

**La serie 500*

- 500 : Error interno del servidor
- 503 : Servicio no disponible


### REST

Rest API es una arquitectura de servicios web, REST:

- RE -> **Representation** : Tipicamente en JSON o XML
- ST -> **State Transfer**  : Tipicamente por HTTP

Es un estandar basado en el protocolo HTTP y por consecuente en los metodos anteriormente nombrados. Para cada metodo, en REST tenemos operaciones que representan las funciones que se podra realizar en la aplicacion. Este estandar tiene restricciones las cuales nacen de la tesis doctoral de Roy Fielding
en el año 2000. 

Los **verbos** utilizados son: 

- GET
- DELETE
- POST
- PUT
- PATCH

Los **mensajes** tanto de respuesta como de solicitud son enviados en formatos:

- XML
- JSON

Los **endpoints o URIS** son los puntos de entrada o puntos de salida de los recursos:

- http://www.example/books/1

Estos siempre van acomapañados a un METODO, una URI no puede tener dos metodos repetidos, es decir http://www.example/books/1 podra tener operaciones POST, DELETE, GET... y porque cada una se diferencia por su operacion.

**HATEOAS**

Significa "Hipermedia como motor del estado de la aplicación", toda aplicacion REST se caracteriza por no tener estado y esto es por utilizar un motor HATEOAS el cual asegura que toda operacion realizada un endpoint no depende o conoce otra operacion, son independientes por lo cual no guarda estado u informacion del resutado de otra operacion. Incluso una vez una operacion es ejecutada y finalizada esta no recordara su resultado en el futuro.

Ejemplo: 

Ante una consulta
```
GET /accounts/12345 HTTP/1.1
Host: bank.example.com
Accept: application/vnd.acme.account+json
```

```
HTTP/1.1 200 OK
Content-Type: application/vnd.acme.account+json
Content-Length: ...

{
    "account": {
        "account_number": 12345,
        "balance": {
            "currency": "usd",
            "value": 100.00
        },
        "links": {
            "deposit": "/accounts/12345/deposit",
            "withdraw": "/accounts/12345/withdraw",
            "transfer": "/accounts/12345/transfer",
            "close": "/accounts/12345/close"
        }
    }
}
```
La respuesta contiene estos posibles enlaces de seguimiento: realizar un depósito, retiro o transferencia, o cerrar la cuenta. Cuando la información de la cuenta se recupera más tarde, la cuenta está sobregirada:

```
HTTP/1.1 200 OK
Content-Type: application/vnd.acme.account+json
Content-Length: ...

{
    "account": {
        "account_number": 12345,
        "balance": {
            "currency": "usd",
            "value": -25.00
        },
        "links": {
            "deposit": "/accounts/12345/deposit"
        }
    }
}
```

**Todas las acciones futuras que el cliente pueda realizar se descubren dentro de las representaciones de recursos devueltas por el servidor.**
Ahora solo hay un enlace disponible: depositar más dinero. En su estado actual, los otros enlaces no están disponibles. De ahí el término Motor de estado de aplicación, ya que las acciones que son posibles varían a medida que varía el estado del recurso.



**CRUD**
Es el termino que se le asigna a la serie de operaciones que esta permitida a un recurso. Por ejemplo, a un libro, podriamos guardarlo en el sistema (CREATE), actualizarlo (UPDATE O PATCH), solo leerlo (READ) o eliminarlo (DELETE).

Cada operacion hereda las mismas caracteristicas de los metodos HTTP (Idempotencia, seguro, etc).

**RICHARDSON MADURITY MODEL (RMM)**

Es un modelo estandarizado propuesto por LEONARD RICHARDSON en 2008  me permite medir la calidad y madurez de mi aplicacion REST.

![image](https://user-images.githubusercontent.com/56406481/216872832-6c9618c0-d2ad-42ff-b829-7dddf15d68bf.png)

- NIVEL 0

Esta dice que se utiliza un formato de estructura de datos para el envio de informacion en un protocolo, en este caso especificamente XML.

- NIVEL 1

Se tienen varias URIS y estas ser consultadas todas por un solo metodo. Por ejemplo: 

Con GET podemos usar los endpoints como: 

http://www.example/books/1

http://www.example/books/120

http://www.example/books/12345

- NIVEL 2

Se hacen uso de verbos HTTPS (GET, POST, DELETE, etc)

GET /books/12345

PUT /books/12345

DELETE /books/12345

- NIVEL 3

Proveer endpoints que le permitan al cliente de la API descubrir la aplicacion y sus endpoints. Para ello se quererira que la API este documentada.

**SPRING RESTFul SERVICES**
Spirng framework tiene un soporte robusto para crear y consumir RESTFul Web Services 

Spring MVC es la libreria mas antigua y comun para crear aplicaciones RESTful web services.

- Es parte del core de Spring Framework, compatible con JAKARTA EE en Spring 6
- Hace uso del patron de diseño MVC (Modelo - Vista - Controlador)
- Basado en el tradicional JAVA SERVLET API
- Por naturaleza es bloqueante, es decir no reactivo

Spring RestTemplate es la principal libreria para consumir API RESTFul web 

- Configurable
- Es una libreria muy madura y robusta que hace tiempo se encuentra en Spring
- Spring recomienda el uso de WebClient como reemplazo a RestTemplate 


Web Client libreria para consumir API RESTFul web

- Fue introducida en Spring Framework version 5
- Es reactiva
- Por default utiliza el Reactor Netty y no es bloqueante

**Marshalling/Unmarshalling**

Marshalling es el acto de pasar de Java POJOs (Objetos JAVA) al formato JSON o XML, por default es JSON
UnMarshalling es el acto de pasar del formato JSON o XML a Java POJOs


**SPA - Single Page Applications**

RESTFul APis se combinan con aplicaciones SPA para enriquecer las aplicaciones de usuarios. Los clientes mas populares que se encuentran trabajando con SPA son los frameworks como VUE, BackBoneJS, ReactJS, AngularJS y EmberJs.

Se abstrae la aplicacion del servidor de la aplicacion que se corre en el front, es decir, no importa y no interesa con que framework de front end se trabaje el proceso de consultas via HTTP/JSON (o XML) y el proceso de Marshalling/Unmarshalling hace la interaccion entre front y back sea transparente.


## Rama 3 - Proyecto Lombok

Lombok es una libreria de Spring el cual nos permite a partir de anotaciones en clases, metodos y atributos ahorrarnos una gran cantidad de codigo.
Dicho codigo, es codigo cotidiano en cada clase, como por ejemplo getters y setters, constructores con o sin argumentos, permite aplicar el patron builder, entre otros.

Lombok permite indicar en anotaciones el codigo que automaticamente, en tiempo de ejecucion se escribirá. Si bien este codigo no se escribe en la clases de archivos .java, si se va a escribir en los archivos .class generados por el JVM. 


- @Getter

Genera para cada atributo de una clase, sus correspondientes getters. 

- @Setter

Genera para cada atributo de una clase, sus correspondientes setters. 

- @RequiredArgsConstructor

Genera un constructor para la clase, que requiere obligatoriamente de cada uno de los argumentos o atributos que incluye la clase. Dichos atributos son marcados con final (Constantes) y como @NotNull es decir no pueden ser nulos.

- @ToString

Genera un metodo que imprime el valor de cada uno de los atributos (mas relevantes) de una clase.

- @EqualsAndHashCode

Genera el metodo que permite comparar si dos objetos de una clase son iguales

- @Data

Es una anotacion que agrupa varias anotaciones, estas son @Getter, @Setter, @RequiredArgsConstructor, @ToString y @EqualsAndHashCode

- @Builder

Crea el patron builder para la clase anotada, con ella es mucho mas sencillo la creacion de objetos. 

Ejemplo: 

```
@Builder
@Data //Equivalente a @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
public class Can {
    private UUID id;
    private String name;
    private String ownerName;
    private LocalDateTime birthDate;
    private String pedigree;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;

}
```
Patron aplicado.
```
        return Can.builder()
                .name("Merci")
                .id(UUID.randomUUID())
                .birthDate(LocalDateTime.of(2022,6,12,0,0,0))
                .ownerName("Abi")
                .pedigree("Pinscher")
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
```


- @Slf4j

Esta anotacion permite aplicar logs, los logs permiten escribir mensajes e ir anunciando en la ejecucion del metodo informacion que necesitemos.

```
log.info("Este es un mensaje que genera gracias a @Slf4j");
```

- @AllArgsConstructor

Crea un constructor con todos los argumentos para una clase.

- @NoArgsConstructor

Crea un constructor vacio, sin argumentos para una clase. 



**Entre muchas otras anotaciones**. Podemos utilizar @Override sobre aquellos metodos en los que **no** queremos que se apliquen los cambios de Lombok.









