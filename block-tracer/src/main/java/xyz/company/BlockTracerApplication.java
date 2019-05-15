package xyz.company;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.util.Map;

@SpringBootApplication
@EnableScheduling
public class BlockTracerApplication {

	@Value("${infura.url}")
	private String infuraUrl;

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(BlockTracerApplication.class);

		app.setDefaultProperties(Map.of( "server.port", "8081"));
		app.run(args);

	}

	@Bean
	public Web3j web3j() {
		return Web3j.build(new HttpService(infuraUrl));
	}
}
