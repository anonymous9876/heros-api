package name.anonymous.heros.api.web.pagination.rest.facet;

import java.util.List;
import java.util.Map;

public class Facet {
	private List<String> propertyPaths;
	private Map<String, RestFilter> filtersByPropertyPath;
	
	public List<String> getPropertyPaths() {
		return propertyPaths;
	}

	public void setPropertyPaths(List<String> propertyPaths) {
		this.propertyPaths = propertyPaths;
	}

	public Map<String, RestFilter> getFiltersByPropertyPath() {
		return filtersByPropertyPath;
	}

	public void setFiltersByPropertyPath(Map<String, RestFilter> filtersByPropertyPath) {
		this.filtersByPropertyPath = filtersByPropertyPath;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Facet [propertyPaths=").append(propertyPaths).append(", filtersByPropertyPath=").append(filtersByPropertyPath).append("]");
		return builder.toString();
	}
}
