package name.anonymous.heros.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class HerosBatchApplication {
	public static void main(String[] args) {
		SpringApplication.run(HerosBatchApplication.class, args);
	}
}
