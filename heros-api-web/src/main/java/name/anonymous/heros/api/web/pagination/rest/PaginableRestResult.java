package name.anonymous.heros.api.web.pagination.rest;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import name.anonymous.heros.api.web.pagination.rest.facet.Facet;

@JsonPropertyOrder({ "draw", "recordsFiltered", "recordsTotal", "facet", "data" })
public class PaginableRestResult<T> {
	private Long recordsFiltered;
	private Long recordsTotal;

	private Iterable<T> data;

	private Facet facet;

	public Long getRecordsFiltered() {
		return recordsFiltered;
	}
	public void setRecordsFiltered(Long recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}
	public Long getRecordsTotal() {
		return recordsTotal;
	}
	public void setRecordsTotal(Long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}
	public Iterable<T> getData() {
		return data;
	}
	public void setData(Iterable<T> data) {
		this.data = data;
	}

	public Facet getFacet() {
		return facet;
	}

	public void setFacet(Facet facet) {
		this.facet = facet;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PaginableRestResult [recordsFiltered=").append(recordsFiltered).append(", recordsTotal=").append(recordsTotal).append(", data=").append(data)
				.append(", facet=").append(facet).append("]");
		return builder.toString();
	}
}

