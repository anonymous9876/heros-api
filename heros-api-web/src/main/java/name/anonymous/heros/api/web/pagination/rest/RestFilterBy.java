package name.anonymous.heros.api.web.pagination.rest;

public class RestFilterBy {
	private String globalSearch;

	public String getGlobalSearch() {
		return globalSearch;
	}

	public void setGlobalSearch(String globalSearch) {
		this.globalSearch = globalSearch;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RestFilterBy [globalSearch=").append(globalSearch).append("]");
		return builder.toString();
	}
}
