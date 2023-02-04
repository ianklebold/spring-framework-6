package jedi.springframework.dependencyinjection;

import jedi.springframework.dependencyinjection.controllers.MyController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class DependencyInjectionPrinciplesApplicationTests {

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	MyController myController;


	//Ambos test devuelven lo mismo. El  @Autowired y tomar el controller del contexto son equivalentes.
	@Test
	void testAutowiredOfController(){
		System.out.println(myController.sayHello());
	}

	@Test
	void testGetControllerFromContext(){
		MyController myController = applicationContext.getBean(MyController.class);

		System.out.println(myController.sayHello());
	}

	@Test
	void contextLoads() {
	}

}
