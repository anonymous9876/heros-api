package name.anonymous.heros.api.web.controller.bean;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class PaginatedRequest {
	@ApiModelProperty(name = "offset", value = "offset", required = false)
	private Integer offset = 0;

	@ApiModelProperty(name = "limit", value = "limit", required = false)
	private Integer limit = -1;

	@ApiModelProperty(name = "sort", value = "sort", required = false, allowEmptyValue = true)
	private List<String> sort = new ArrayList<>();

	@ApiModelProperty(name = "search", value = "search", required = false)
	private String search = "";

	@ApiModelProperty(name = "filter", value = "filter", required = false)
	private String filter;

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public List<String> getSort() {
		return sort;
	}

	public void setSort(List<String> sort) {
		this.sort = sort;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	@Override
	public String toString() {
		return "PaginatedRequest [offset=" + offset + ", limit=" + limit + ", sort=" + sort + ", search=" + search + ", filter=" + filter + "]";
	}
}
