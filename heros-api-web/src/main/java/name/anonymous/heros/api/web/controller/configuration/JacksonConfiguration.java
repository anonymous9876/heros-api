package name.anonymous.heros.api.web.controller.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class JacksonConfiguration {
	
	@Bean
	public Jdk8Module jdk8Module() {
		return new Jdk8Module();
	}
	
	@Bean
	public JavaTimeModule javaTimeModule() {
		return new JavaTimeModule();
	}
}
