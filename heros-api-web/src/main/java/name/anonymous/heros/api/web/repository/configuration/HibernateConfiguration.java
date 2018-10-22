package name.anonymous.heros.api.web.repository.configuration;

import java.util.Collections;
import java.util.Map;

import org.hibernate.jpa.boot.spi.IntegratorProvider;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Configuration;

import name.anonymous.heros.api.web.repository.configuration.hibernate.integrator.MetadataExtractorIntegrator;

@Configuration
public class HibernateConfiguration implements HibernatePropertiesCustomizer {
    @Override
    public void customize(Map<String, Object> hibernateProperties) {
	hibernateProperties.put("hibernate.integrator_provider",
		(IntegratorProvider) () -> Collections.singletonList(MetadataExtractorIntegrator.INSTANCE));
    }
}
