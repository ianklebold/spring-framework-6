package jedi.springframework.dependencyinjection;

import jedi.springframework.dependencyinjection.controllers.MyController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DependencyInjectionPrinciplesApplication {

	public static void main(String[] args) {

		System.out.println("Recuperando el bean del contexto");
		ConfigurableApplicationContext contexto = SpringApplication.run(DependencyInjectionPrinciplesApplication.class, args);

		//Spring guarda en su contexto a todos los beans (clases y metodos que tiene anotaciones como @Service, @Component, @Controller, etx)
		//Luego los retoma cuando tiene que hacer la inyeccion de dependencias
		//Esto es equivalente a hacer @Autowired
		System.out.println("Resultado del controller : "+ contexto.getBean(MyController.class).sayHello());


	}

}
