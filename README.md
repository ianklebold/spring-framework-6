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


## Rama 4 - SPRING MVC REST SERVICES

MVC consiste en tres tipos de objetos. El modelo es el objeto de la aplicacion, La vista es su representacion en la pantalla y el Controlador define el modo en que la interfaz reacciona a la entrada del usuario. Antes, cuando MVC no existia, se tendia a agrupar todos estos objetos en uno solo. MVC buscara separarlos incrementando la flexibilidad y reutilizacion.

MVC logra desacoplar a estos objetos, de tal manera que si alguno de ellos cambia entonces afecta a otros con los que se relaciona, hay una minima conexion entre estas. Esta caracteristica es descripta en mas detalle con el **Patrón Observer**

MVC encapsula la forma de responder la informacion que necesita la vista, es decir la forma en que recibe la entrada, la procesa y la retorna. Una vista puede no cambiar la forma de representar la informacion de salida pero puede procesarla de diferentes maneras, este desacople queda mejor definido con el **Patrón Strategy**.

MVC permitira a la vista componerse de otros elementos o subelementos que compoenen a lo que llamaremos "Vista compuesta", dicha composicion no interfiere ni afecta a los demas objetos (Modelo y Vista). Esto es posible y mejor definido a partir del **Patrón Composite**.

Entonces MVC se encuentra principalmente diseñado a partir de los patrones de diseño Observer, Composite y Strategy. 

SPRING MVC a partir de aplicaciones REST permite capturar la operacion que el usuario desea realizar, capturar los datos de entrada y procesarlos (Controlador y Modelo) para finalmente responder con informacion de salida que en este caso al ser REST por una formalidad y estandarizacion se utiliza JSON o XML (Vista).

Para una mejor abstraccion, utilizamos las clases Services cuyo fin es incorporar y manejar la logica de negocio dejando al controlador la unica responsabilidad de interceptar el pedido, colaborar con el servicio que maneje la peticion y retornar una respuesta. 

@RequestMapping 
Es la anotacion utilizada por Spring, el cual acepta cualquier tipo o metodo de operacion (POST, PUT, PATCH, DELETE, GET, etc). Generalmente acompañada por un value el cual indica la URI o punto en donde la aplicacion provee un servicio.

El servicio para los perros se encuentra en "/api/v1/dogs"
```
@RequestMapping(value = "/api/v1/dogs")
```

Pero la RequestMapping acepta cualquier tipo de operacion, lo que buscamos ahora es separar las operaciones y tratarlas de diferente modo, para ello usamos a nivel de metodos otra anotacion.

@DeleteMapping 
Generalmente acompañada de una URI y variables el cual indica que recurso se desea eliminar.

Luego de ejecutar la operacion se debe devolver 

- ResponseEntity -> Resultado de la operacion, dicho Response no debe tener contenido.

@UpdateMapping
Generalmente acompañada de una URI y variables que indiquen que recurso se desea actualizar. A su vez es indispensable enviar los datos con los que vamos a actualizar el objeto (recurso).

Luego de ejecutar la operacion se debe devolver 

- ResponseEntity -> Resultado de la operacion, dicho Response no debe tener contenido.

@PostMapping
Utilizada para crear un nuevo objeto (recurso) en la aplicacion.


- Header -> Donde puede consultar por el recurso recien creado
- ResponseEntity -> Resultado de la operacion

@GetMapping
Utilizada para retornar informacion.

@RequestBody
Comunmente utilizada para el Update y Post se deben enviar los datos con los que se desea actualizar o crear un objeto, para esto hacemos uso de la notacion  el cual va acompañada del modelo que contiene los datos.

@PathVariable
Comunmente utilizada para Update y Delete, con el indicamos que recurso que se desea ejecutar una operacion, por ejemplo, que recurso se desea eliminar o actualizar. 


## Rama 5 - SPRING MVC TEST CON MOCKITO

En esta seccion vamos a ver el uso de Mock MVC para probar nuestros endpoints en las clases controller. Mock MVC sigue la siguiente piramide de Tests

![PyramidOfTesting](https://user-images.githubusercontent.com/56406481/221850633-c986831d-ebda-426b-a3f8-6c9d209845cb.png)

En su libro, Succeeding with Agile: Software Development Using Scrum, Mike Cohn describe la pirámide de automatización de los tests como una estrategia de automatización de tests en términos de cantidad y esfuerzo que deben dedicarse a cada uno de los tipos de test.

La base de la pirámide son los tests unitarios, por lo que debemos entender que los tests unitarios son los cimientos de la estrategia de testing y que debería de haber muchos más tests unitarios que tests end-to-end de alto nivel. Luego siguen los test de integracion, es decir probar los modulos o partes del sistema interactuando entre si y por ultimo tenemos los test de funcionalidades las cuales son llevadas a cabo junto con el usuario.

Los tests deben hacerse de forma aislada, cada parte del software no debe depender de otra, por ejemplo el Controller no debe depender del Service para poder llevar cabo los test y para conseguir esto normalmente aquellas partes en las cuales no nos enfocamos en probar las simulamos (Mocks)

Gerard Meszaros identifico cinco tipos de objetos que entran en la cateogria informal de mock: dummies, stubs, spies, mocks y fakes, se las denomino dobles de pruebas. Su funcion es sustituir un objeto por otrro mientras se ejecuta la prueba. Muy util cuando aquella parte simulada aun no esta completa o simplemente (Y como buena practica) nos abstraemos de ella. 

Los dobles de prueba forman una especie de jerarquia de tipos. Los dummies son los mas simples. Los stubs son dummies, los spies son stubs y los mocks son spies. Los fakes son independientes. El mecanismo que utilizan todos estos dosbles de pruebas es simplemente **polimorfismo**. 

Por ejemplo, si quiere probar el codigo que gestiona un servicio externo, aisla ese servicio externo detras de una interfaz polimorfica y despues crea una implementacion de esa interfaz que representa al servicio. Esa implementacion es el doble de pruebas.

<img width="1369" alt="test-doubles-overview" src="https://user-images.githubusercontent.com/56406481/221853238-e574cce2-5f4c-42e9-a089-9c58d6f1984e.png">



### DUMMY

Es un doble de pruebas que implementa una interfaz para no hacer nada. Se utiliza cuando lafuncion quie esta probandose toma un objeto como argumento, pero la logica de esa prueba no requiere que ese objeto este presente. 


### STUB 

Un Stub es un Dummy que se implementa para que no haga nada. Sin embargo, en vez de devolver cero o null, las funciones de un stub devuelve valores que guian a la funcion que esta probandose a traves de las rutas que la prueba quiere que se ejecuten

### SPY

Un Spy es un stub. Devuelve valores especificos de la prueba para guiar al sistema que esta probandose a traves de las rutas deseadas. Sin embargo, un spy recuerda lo que se le ha hecho y permite a la prueba preguntar al respecto. Un spy puede ser tan simple como un unico booleano que se establece cuando se llama a un metodo en particular, o puede ser un objeto relativamente complejo que mantiene un historial de todas las llamadas y todos los argumentos que se han pasado a cada llamada.

### MOCK

Un mock es un spy. Devuelve valores especificos de la prueba para guiar al sistema que esta probandose a traves de las rutas deseadas y recuerda lo que se le ha hecho. Sin embargo, un mock tambien sabe que esperar y pasara o fallara la prueba de acuerdo a esas expectativas.

### FAKE

Un fake no es un dummy, un stub, un spy ni un mock. Un fake es un tipo totalmente diferente de doble de pruebas. Es un simulador. 
Un Fake puede reemplazar por completo un software o una parte de el. Los Fakes integran reglas de nogocio, logica con el fin de poder comportarse como nostros deseemos. 


**Spring se encarga de crear estos mocks por nosotros**

- MockMvc nos permite testear las interacciones con entre el controlador y el servlet sin que la aplicacion este corriendo en el servidor.
- @MockBean Con este decimo que el componente o servicio que queremos simular pase a ser del contexto de Test de Spring
- ObjectMapper Es una clase ya existente por defecto en el contexto de spring, permite convertir los objetos a json (Unmarshing), son ideales para las pruebas donde se debe enviar un Request Body.
- @Captor Es el encargado de inyectar un capturador de argumentos
- ArgumentCaptor Es el capturador de argumentos que se pasen por un endpoint, puede ser lo que sea desde IDs, Cuerpos de objetos, etc.

En este caso nuestro enfoque de prueba es el controller CanControllerTest.java y simulamos el servicio.

```
given(canService.getCanById(canTest.getId())).willReturn(canTest);
```
Puede observar que en este caso canService es una interfaz, no una implementacion. Este es un perfecto caso de un Stub, en donde no tenemos implementacion y retorna algo en concreto en este caso un objeto. en este metodo estamos diciendo que dado un servicio con un metodo denominado ```getCanById``` obtenemos un objeto ```canTest```.

Hay casos en donde los servicios no devuelven nada, pero nos interesa controlar que al menos sea ejecutada o llamada en el controlador o que los argumentos que se le envian son validos. 

```
verify(canService).deleteById(uuidArgumentCaptor.capture());

assertThat(canTest.getId()).isEqualTo(uuidArgumentCaptor.getValue());
```

Con verify controlamos si ejecuta o no el metodo deleteById dentro del metodo cuando se invoque un endpoint y con el captor obtenemos el ID para luego compararlo. 

Lo comparamos con asertores.

Tanto given como verify se lo llama en el test y se lo controla cuando el endpoint es llamado.

## Rama 6 - EXCEPTION HANDLING

En esta seccion creamos una nueva aplicacion MVC, un CRUD y sus respectivos test en los controllers. El enfoque aca fue entender la importancia de centralizar el manejo de los errores. 

Cada error es ejecutado por una excepcion y ante cualquier error debe emitirse un mensaje y un codigo de error web que informe al usuario del motivo del error. 

La idea es centralizar toda excepcion en un solo lugar, de tal manera que al retornar una excepcion Spring sea capaz de tomarla, darle forma y mostrarla adecuadamente al usuario.


Lo primero que debemos generar son nuestras Excepciones Custom: 

```
public class NotFoundException extends Exception{
    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }

    public NotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
```

La cual sera invocada, cuando: 

- Durante la ejecucion del/los servicios que el controlador este manejando retornen una excepcion
- Cuando un servicio retorne un valor no deseado al controlador, sera el controlador quien llame a la excepcion custom

El ultimo caso, se representaria de la siguiente forma:

**CarsServiceImpl.java**
```
@Override
public Optional<Car> getCarByUUID(UUID uuid) {
   return Optional.of(carsMap.get(uuid));
}
```
Si el servicio obtiene un UUID el cual no se encuentra en el repositorio de cars, entonces retorna un Optiona.Empty() (vacio)

**CarsController.java**
```
@GetMapping(value = CAR_PATH_ID)
public Car getCarByUUID(@PathVariable UUID uuidCar) throws Exception {

     return carService.getCarByUUID(uuidCar).orElseThrow(NotFoundException::new);
     
 }
```

El controlador hace uso de Optional, el cual al mandar un uuid de un car, si como resultado me viene un Optional.Empty() entonces ejecutamos la Excepcion 

Por otro lado, la excepcion retornada:
```
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Value not found")
public class NotFoundException extends Exception{
    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }

    public NotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
```

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Value not found") Lo que hace es adjuntar un valor HTTP y un mensaje o razon de la excepcion.



### ControllerAdvice

ControllerAdvice es utilziado cuando deseamos retornar un mensaje y codigo de error para una excepcion en particular que puede ser emitida por cualquier controlador.

```
@ControllerAdvice
public class ExceptionController{

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFoundException(){
        return ResponseEntity.notFound().build();
    }

}
```
De esta forma, cualquier controller que retorne una excepcion la tomara y la reemplazara por NotFoundException.class con el codigo de error Not Found.


## Rama 7 - SPRING DATA JPA

Spring framework provee:

+ JTA 
Java transaction API que es la gestion de transacciones. Las transacciones me garantizan que se realicen operaciones atomicas y que estas se ejecuten de manera completa y de no ser asi vuelva a un estado seguro (Rollback).

@Transactional
Se lo coloca en los metodos, donde decimos que todo lo que se gestione con la base de datos en ese metodo son operaciones atomicas.

+ JDBC
Java Database Connectivity es un conector, es una abstraccion a la BASE DE DATOS. Desde el punto de vista de JAVA permitiendome escribir SQL en JAVA.

+ R2DBC
Lo mismo que JDBC pero reactiva

+ ORM
Permite a partir de anotaciones convertir nuestras clases en entidades en la base de datos y tambien relacionar estas entidades (Uno a uno, uno a muchos, muchos a muchos, etc). 
Como ORM soporta JPA y Hibernate nativo.

A su vez, Spring DATA provee implementaciones para el DAO (Data access object), es decir, son implementaciones de operaciones a la base de datos pero ya implementadas, por default hace uso de HIBERNATE como motor. 


### Convertir las clases en entidades

**@Entity**

```
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Car {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36,columnDefinition = "varchar",updatable = false,nullable = false)
    private UUID id;

    @Version
    private Integer version;

    private String model;
    private int yearCar;
    private String patentCar;
    private String size;
    private String make;
    private String fuelType;

    private LocalDateTime createCarDate;
    private LocalDateTime updateCarDate;
}
```
### DTO Data Transfer Objects

Son objetos o "duplicaciones" de las entidades. Estos DTOs son objetos cuyo fin es ser utilizado y transferido en las capas de servicio y controller dejando a las entidades solamente para la capa de persistencia. A su vez disminuye la cantidade anotaciones que se le tienen que poner a las clases entidades ya que son los DTOs los que interactuan en con el usuario y con la capa logica.

- Los DTOs son objetos que interactuan con el usuario y tienen anotaciones/restricciones para la capa del negocio de la aplicacion
- Las Entidades son objetos que las devuelve la capa de persistencia a la de negocio para transformarlas a DTOs y a su vez la capa de persistencia recibe entidades para persistirlas o realizar otras operaciones de CRUD en la base de datos.

![image](https://user-images.githubusercontent.com/56406481/222797545-bacbe0d9-01ac-4cb8-b224-474be85f7b3a.png)

Para lograr esta transformacion de DTO a Entidad y viceversa hacemos uso de **MapStruct**, es una libreria el cual nos permite de una menera muy sencilla realizar esta conversion.

```
@Mapper
public interface CarMapper {
    Car carDtoToCar(CarDTO dto);

    CarDTO carToCarDto(Car car);
}
```
Cuando estos se compilan se crean Spring crea la implementacion de estos: 

![image](https://user-images.githubusercontent.com/56406481/222798678-73f2946a-7e52-42c6-8efc-e6e9e83fa4b3.png)

La implentacion es un @Component

```
@Component
public class CarMapperImpl implements CarMapper {
    public CarMapperImpl() {
    }

    public Car carDtoToCar(CarDTO dto) {
        if (dto == null) {
            return null;
        } else {
            Car.CarBuilder car = Car.builder();
            car.id(dto.getId());
            car.version(dto.getVersion());
            car.model(dto.getModel());
            car.yearCar(dto.getYearCar());
            car.patentCar(dto.getPatentCar());
            car.size(dto.getSize());
            car.make(dto.getMake());
            car.fuelType(dto.getFuelType());
            car.createCarDate(dto.getCreateCarDate());
            car.updateCarDate(dto.getUpdateCarDate());
            return car.build();
        }
    }

    public CarDTO carToCarDto(Car car) {
        if (car == null) {
            return null;
        } else {
            CarDTO.CarDTOBuilder carDTO = CarDTO.builder();
            carDTO.id(car.getId());
            carDTO.version(car.getVersion());
            carDTO.model(car.getModel());
            carDTO.yearCar(car.getYearCar());
            carDTO.patentCar(car.getPatentCar());
            carDTO.size(car.getSize());
            carDTO.make(car.getMake());
            carDTO.fuelType(car.getFuelType());
            carDTO.createCarDate(car.getCreateCarDate());
            carDTO.updateCarDate(car.getUpdateCarDate());
            return carDTO.build();
        }
    }
}
```


### Conexion a H2

Todo esto es imposible sin una base de datos, en este caso la conexion es a traves de H2, una base de datos preferida para TEST y de muy facil uso. Esta base de datos inicia y crea las tablas cuando iniciamos el servidor de Spring y se elimina cuando la cerramos.

```
<dependency>
	<groupId>com.h2database</groupId>
	<artifactId>h2</artifactId>
	<version>{version-h2}</version>
	<scope>test</scope>
</dependency>
```

### Test de datos

Para las distintas pruebas, optamos por crear unos datos falsos. Estos datos falsos son cargados cada vez que la aplicacion inicia.

Podemos testear la capa de datos, con Junit, con la notacion @DataJpaTest.

```
@DataJpaTest
class BootstrapDataTest {

    @Autowired
    CarRepository carRepository;

    @Autowired
    CustomerRepository customerRepository;

    BootstrapData bootstrapData;

    @BeforeEach
    void setUp(){
        //Como lo necesitamos para cada uno de los test, no lo inyectamos con autowired sino manualmente
        bootstrapData = new BootstrapData(carRepository,customerRepository);
    }

    @Test
    void load_test_data() throws Exception {
        bootstrapData.run(null);

        assertThat(carRepository.count()).isEqualTo(3);
        assertThat(customerRepository.count()).isEqualTo(3);
    }

}
```
Con @DataJpaTest mandamos en el contexto de Test los beans necesarios para los test de base de datos y nada mas que eso.

### Implementacion de DAO

Spring Data nso trae la implementacion de los DAO. Esto se logra a partir de interfaces, el cual nos brinda un conjunto de metodos y servicios ya escritos por Spring para hacer uso de ellos y poder interactuar con la base de datos.

Con el podemos hacer operaciones como la de guardar en base de datos, actualizar, eliminar, buscar por algun/os atributo/s, crear, etc.

La implementacion para nuestra entidad: 

```
public interface CarRepository extends JpaRepository<Car, UUID>{ }
```


### Test de integracion

Ahora nos toca crear un test que nos permita probar, el controller, servicio y repositorio.


Como lo que vamos a probar es la integracion de las partes necesitamos SpringBootTest, es decir tomar del contexto a todos los beans.

@SpringBootTest

En este nuevo test vemos dos anotaciones nuevas: 

@Rollback
@Transactional

Como en cada test interacutamos con la base de datos, necesitamos reponer nuestra base de datos para proximos tests, entonces con @Rollback indicamos que una vez finalizado un test vuelva a la base de datos al estado anterior (Segura) para que se encuentre preparado para los proximos tests.

Con @Transactional, como anteriormente mencione indica que el metodo anotado ejecutara operaciones atomicas, pequeñas, las cuales si alguna operacion falla toda la  serie de operacioens que se pudieron ejecutar en el metodo se cancelan devolviendo a la base de datos en un estado seguro, lo cual se espera que todas las operaciones se completen de lo contrario no se hace nada. 



## Rama 8 - Data Validation

Podremos validar datos desde dos puntos, del lado del servidor y el usuario. Ambos son muy importantes que tengan validacion de los datos que son ingresados.

En nuestro caso como estamos del lado del servidor, tenemos distintas librerias  y anotaciones que nos permiten componer **restricciones** para los atributos de nuestras clases.

### Constraints

Las restricciones del lado de los objetos que son cargados por el usuario vienen dadas por la dependencia Validation, el cual nos brinda una serie de anotaciones que los podemos colocar en nuestras clases. Todas estas anotaciones indican a Spring que el atributo que es cargado por el usuario debe cumplir ciertas restricciones.

![image](https://user-images.githubusercontent.com/56406481/222926073-b1b2cd2c-8eca-4cb6-8e24-8a325c6ae85c.png)

En nuestro caso lo pondremos en nuestro DTO

```
public class CarDTO {

    private UUID id;
    private Integer version;

    @NotBlank
    @NotNull
    private String model;

    @NotNull
    private int yearCar;

    @NotBlank
    @NotNull
    private String patentCar;

    @NotBlank
    @NotNull
    private String size;

    @NotBlank
    @NotNull
    private String make;

    @NotBlank
    @NotNull
    private String fuelType;

    private LocalDateTime createCarDate;
    private LocalDateTime updateCarDate;
}
```

Todas estas tienen ciertas restricciones que deben de ser cumplidas sino lanzara una excepcion. Dicha excepcion será lanzada desde el controller donde llega el DTO.

La anotacion **@Validated** delante del @RequstBody del DTO le indica a Spring que el objeto que se manda por parametro tiene restricciones.

```
@PostMapping(value = CAR_PATH)
public ResponseEntity createCar(@Validated @RequestBody CarDTO carDTO){
        CarDTO carDTOCreated = carService.createCar(carDTO);

        HttpHeaders headers = new HttpHeaders();

        headers.add("Location",CAR_PATH+"/"+ carDTOCreated.getId());

        return new ResponseEntity(headers, HttpStatus.CREATED);
}
```

Ahora bien, esta excepcion (MethodArgumentNotValidException) es poco amigable por el usuario, por lo que conviene capturar la excepcion customizarla y presentarla adecuadamente al usuario. 

Para ello creamos una clase con la anotacion **@ControllerAdvice** para centralizar todas las excepciones que todos los controladores pueden retornar. Y en ella ponemos un handler excepcion para la excepcion en particular. 

```
@ExceptionHandler(MethodArgumentNotValidException.class) //Este es la excepcion que manda spring cuando @Valited falla
ResponseEntity handleBindErrors(MethodArgumentNotValidException exception){

//Obtenemos todos los errores
List errorList = exception.getFieldErrors().stream()
		  .map(fieldError -> {
                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put(fieldError.getField(),fieldError.getDefaultMessage());
                    return errorMap;
                }).collect(Collectors.toList());

        return ResponseEntity.badRequest().body(errorList);
}
```


### Hibernate Constraints

Hibernate a su vez tiene una serie de restricciones, que son propias de la base de datos, por ejemplo una restriccion seria ingresar una cadena de String mayor 255 en un campo siendo que 255 es el liminte, o la de no actualizable o no null, etc..

Estas restricciones pueden ser customizadas con la notacion de @Column y las anteriores mostradas. Estas restricciones por supuesto tienen que ir en la clase Entidad no en el DTO, porque es la Entidad la que se mapeara en registros en la base de datos.

Esta separacion entre DTO y Entidad nos permite separar restricciones, siendo las restricciones a nivel de base de datos y persistencia la que tiene la Entidad y las demas las del DTO.

```
public class Car {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36,columnDefinition = "varchar",updatable = false,nullable = false)
    private UUID id;

    @Version
    private Integer version;

    @NotNull
    @NotBlank
    @Size(max = 50)
    @Column(length = 50)
    private String model;

    private int yearCar;

    private String patentCar;

    private String size;

    private String make;

    private String fuelType;

    private LocalDateTime createCarDate;
    private LocalDateTime updateCarDate;
}
```

Como hicimos anteriormente, el incumplimiento de estas restricciones retornara excepciones la cuales deben ser capturadas, customizadas y presentadas amigablemente. Todo esto puede ser controlado desde la clase con @ControllerAdvice.


## MYSQL with Spring Framework

Para añadir la base de datos MYSQL a nuestro proyecto spring, necesitamos escribir en el POM su respectiva dependencia:

```
<dependency>
	<groupId>mysql</groupId>
	<artifactId>mysql-connector-java</artifactId>
	<version>{Version.mysql}</version>
</dependency>

```

Si estabamos usando H2 y no queremos eliminar su dependencia del proyecto, podemos correr la aplicacion con las dos BD al mismo tiempo, configurando el scopo de H2 a runtime.

```
<dependency>
	<groupId>com.h2database</groupId>
	<artifactId>h2</artifactId>
	<version>2.1.214</version>
	<scope>runtime</scope>
</dependency>
```

Creamos un archivo .properties para configurar la base de datos. **application-localmysql.properties** y configuramos de la siguiente forma:

```
spring.datasource.username=root
spring.datasource.password=1234
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/restdb?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC
spring.jpa.database=mysql
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=update
```

Al momento de iniciar la aplicacion nos producira un error, mysql no iniciara, por lo que podemos configurar INTELLIJ para que inicie la aplicacion a partir de localmysql.properties forzando asi el inicio de mysql.


![Captura desde 2023-03-04 18-19-29](https://user-images.githubusercontent.com/56406481/222929653-4bf11ca6-060c-449c-abfb-f6076340031a.png)

En caso de tener errores de base de datos (Cuando se crean las tablas o se insertan datos) es recomendable un buen Logging de los errores por consola, para ellos usamos logging de Hibernate y no de mysql ya que nos permiten ver las operaciones de la base de datos mientras se ejecuta la aplicacion.

La configuracion recomendada es: 

```
#Show SQL
spring.jpa.properties.hibernate.show_sql=true

#Format SQL
spring.jpa.properties.hibernate.format_sql=true

#Show bind values of the SQL
#No es convieniente mostrar los datos que se cargan en las tablas en el ambiente de PRODUCCION
logging.level.org.hibernate.orm.jdbc.bind=trace
```
Ejemplos:
![Captura desde 2023-03-04 18-19-43](https://user-images.githubusercontent.com/56406481/222929854-a9ba9815-f8e3-4171-b57a-dc8202ee7c21.png)
![Captura desde 2023-03-04 18-20-12](https://user-images.githubusercontent.com/56406481/222929858-b4dc6b0f-1170-4449-a188-20ff20bb153c.png)

Gracias al buen logging, pudimos detectar errores

Añadimos las siguientes correcciones en la clase Car para arreglarlo. 

```
public class Car {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
    
    @JdbcTypeCode(SqlTypes.CHAR) //Aqui le decimos a Hibernate que el UUID lo mandemos como CHAR y no como Binary
    @Column(length = 36,columnDefinition = "varchar(36)",updatable = false,nullable = false)
    private UUID id;
```

- @JdbcTypeCode(SqlTypes.CHAR) 
COn este le decimos a Hibernate que convierta este valor binario a Char (para la base de datos).

- @Column(length = 36,columnDefinition = "varchar(36)",updatable = false,nullable = false)

columnDefinition = "varchar(36)" es un arreglo que mysql especificamente pedia.













