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


## Rama 3 - Introduccion a RestFul web services

El proceso de conversion de POJOs a Json o XML se llama Marshallings
El proceso de convertir Json o XML a POJOs se llama Unmarshallings




