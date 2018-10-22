package name.anonymous.heros.api.web.configuration.dozer.CustomFieldMapper;

import org.dozer.CustomFieldMapper;
import org.dozer.classmap.ClassMap;
import org.dozer.fieldmap.FieldMap;
import org.hibernate.collection.internal.AbstractPersistentCollection;

public class MyCustomFieldMapper implements CustomFieldMapper {
	public boolean mapField(Object source, Object destination, Object sourceFieldValue, ClassMap classMap, FieldMap fieldMapping) {
		// if field is initialized, Dozer will continue mapping

		// Check if field is derived from Persistent Collection
		if (!(sourceFieldValue instanceof AbstractPersistentCollection)) {
			// Allow dozer to map as normal
			return false;
		}

		// Check if field is already initialized
		if (((AbstractPersistentCollection) sourceFieldValue).wasInitialized()) {
			// Allow dozer to map as normal
			return false;
		}

		return true;
	}
}
