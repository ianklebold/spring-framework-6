package jedi.followmypath.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
public class FollowMyPathApiRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(FollowMyPathApiRestApplication.class, args);
	}

}
