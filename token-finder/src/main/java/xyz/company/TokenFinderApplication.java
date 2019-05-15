package xyz.company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

@SpringBootApplication
public class TokenFinderApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(TokenFinderApplication.class);

		app.setDefaultProperties(Map.of( "server.port", "8082"));
		app.run(args);

	}

}
