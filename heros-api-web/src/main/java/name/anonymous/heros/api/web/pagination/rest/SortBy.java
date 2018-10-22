/**
 * 
 */
package name.anonymous.heros.api.web.pagination.rest;

import java.util.HashMap;
import java.util.Map;

public class SortBy {
	private Map<String, SortOrder> sortBys;
	public SortBy() {
		sortBys = new HashMap<>();
	}
	/**
	 * Gets the sort bys.
	 *
	 * @return the sortBys
	 */
	public Map<String, SortOrder>  getSortBys() {
		return sortBys;
	}
	
	/**
	 * Adds the sort.
	 *
	 * @param sortBy the sort by
	 */
	public void addSort(String sortBy) {
		sortBys.put(sortBy, SortOrder.ASC);
	}
	
	/**
	 * Adds the sort.
	 *
	 * @param sortBy the sort by
	 * @param sortOrder the sort order
	 */
	public void addSort(String sortBy, SortOrder sortOrder) {
		sortBys.put(sortBy, sortOrder);
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SortBy [sortBys=").append(sortBys).append("]");
		return builder.toString();
	}
}
