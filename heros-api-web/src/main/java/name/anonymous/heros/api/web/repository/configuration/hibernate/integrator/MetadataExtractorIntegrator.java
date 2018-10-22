package name.anonymous.heros.api.web.repository.configuration.hibernate.integrator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.model.relational.Database;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.mapping.Component;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.Value;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;

public class MetadataExtractorIntegrator implements Integrator {

	public static final MetadataExtractorIntegrator INSTANCE = new MetadataExtractorIntegrator();

	private Database database;

	private Metadata metadata;

	public Database getDatabase() {
		return database;
	}

	public Metadata getMetadata() {
		return metadata;
	}

	public List<String> getEntityPropertyPaths(Class<?> clazz) {
		List<String> entityPropertiesPath = new ArrayList<>();
		resultMappingAux(entityPropertiesPath, "", clazz);
		return entityPropertiesPath;
	}

	private void resultMappingAux(List<String> propertiesPath, String prefix, Class<?> clazz) {

		PersistentClass persistentClass = metadata.getEntityBinding(clazz.getName());
		for (Iterator<?> propertyIterator = persistentClass.getPropertyIterator(); propertyIterator.hasNext();) {
			Property property = (Property) propertyIterator.next();
			if (!property.isLazy() &&
				null == metadata.getEntityBinding(property.getValue().getType().getReturnedClass().getName())
) {
				String newPrefix = ("".equals(prefix) ? prefix : prefix + ".") + property.getName();
				if (property.isComposite()) {
					Value value = property.getValue();
					if (value instanceof Component) {
						Component component = (Component) value;
						for (Iterator<?> propertyIterator2 = component.getPropertyIterator(); propertyIterator2
								.hasNext();) {
							Property property2 = (Property) propertyIterator2.next();
							propertiesPath.add(newPrefix + "." + property2.getName());
						}
						// resultMappingAux(propertiesPath, newPrefix,
						// property.getType().getReturnedClass());
					}
				} else {
					propertiesPath.add(newPrefix);
				}
			}
		}
	}

	@Override
	public void integrate(Metadata metadata, SessionFactoryImplementor sessionFactory,
			SessionFactoryServiceRegistry serviceRegistry) {

		this.database = metadata.getDatabase();
		this.metadata = metadata;

	}

	@Override
	public void disintegrate(SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
	}
}