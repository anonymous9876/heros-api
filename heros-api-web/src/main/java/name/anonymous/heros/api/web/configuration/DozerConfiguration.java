package name.anonymous.heros.api.web.configuration;

import static org.dozer.loader.api.TypeMappingOptions.beanFactory;

import java.util.Collections;
import java.util.UUID;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.TypeMappingOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import name.anonymous.heros.api.web.configuration.dozer.beanFactory.UuidBeanFactory;
import name.anonymous.heros.api.web.model.entity.Mission;
import name.anonymous.heros.api.web.model.entity.ProductLineItem;

@Configuration
public class DozerConfiguration {
	@Bean
	public DozerBeanMapper dozerBean() {
		DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
		dozerBeanMapper.setMappingFiles(Collections.singletonList("dozerJdk8Converters.xml"));

		dozerBeanMapper.addMapping(new BeanMappingBuilder() {
			@Override
			protected void configure() {
				mapping(UUID.class, UUID.class, beanFactory(UuidBeanFactory.class.getName()));
			}
		});

		dozerBeanMapper.addMapping(new BeanMappingBuilder() {
			@Override
			protected void configure() {
				mapping(Mission.class, Mission.class, TypeMappingOptions.mapNull(false));
				mapping(ProductLineItem.class, ProductLineItem.class, TypeMappingOptions.mapNull(false));
			}
		});

		return dozerBeanMapper;
	}
}
