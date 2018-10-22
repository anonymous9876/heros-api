package name.anonymous.heros.api.web.pagination.rest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import name.anonymous.heros.api.web.controller.bean.PaginatedRequest;
import name.anonymous.heros.api.web.pagination.query.builder.JpaQueryBuilderFactory;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.builder.JpaBuilder;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.model.result.JpaQueryResult;

public class RestPaginationCriteria {
	private static final Logger LOGGER = LoggerFactory.getLogger(RestPaginationCriteria.class);

	private String filter;
	private SortBy sortBy;
	/**
	 * lines number to skip
	 */
	private Integer offset;

	/**
	 * max lines by page
	 */
	private Integer limit;
	private RestFilterBy filterBy;

	public RestPaginationCriteria(PaginatedRequest paginatedRequest) {
		Integer offset;
		Integer limit;
		List<String> sort;
		String search;
		filter = null;
		offset = paginatedRequest.getOffset();
		limit = paginatedRequest.getLimit();
		sort = paginatedRequest.getSort();
		search = paginatedRequest.getSearch();
		if (paginatedRequest.getFilter() != null) {
			try {
				filter = URLDecoder.decode(paginatedRequest.getFilter(), StandardCharsets.UTF_8.toString());
			} catch (UnsupportedEncodingException e) {
				LOGGER.error("filter invalid", e);
			}
		}
		SortBy sortBy = new SortBy();
		for (String field : sort) {
			String[] orderBy = field.split(":");
			String propertieName = orderBy[0];
			String sortOrder = orderBy[1];
			sortBy.addSort(propertieName, SortOrder.valueOf(sortOrder));
		}

		RestFilterBy filterBy = new RestFilterBy();
		filterBy.setGlobalSearch(search);

		this.sortBy = sortBy;
		this.offset = offset;
		this.limit = limit;
		this.filterBy = filterBy;
	}

	public SortBy getSortBy() {
		return sortBy;
	}

	public void setSortBy(SortBy sortBy) {
		this.sortBy = sortBy;
	}

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

	public RestFilterBy getFilterBy() {
		return filterBy;
	}

	public void setFilterBy(RestFilterBy filterBy) {
		this.filterBy = filterBy;
	}

//	public SqlQueryResult getSqlQueryResult(String alias) {
//		SqlQueryResult sqlQueryResult = null;

//		if (hasFiltering()) {
			// get SqlBuilder
//			SqlQueryBuilderConfig sqlQueryBuilderConfig = new SqlQueryBuilderConfig();
//			SqlQueryBuilderFactory sqlQueryBuilderFactory = new SqlQueryBuilderFactory(sqlQueryBuilderConfig);
//			removeFilter(sqlQueryBuilderFactory, SqlInjectionAttackFilter.class);
//            addUnescapeFieldFilter(alias, sqlQueryBuilderFactory);
//			SqlBuilder sqlBuilder = sqlQueryBuilderFactory.builder();


			// build query
//			try {
//				sqlQueryResult = sqlBuilder.build(filter);
//			} catch (Exception e) {
//				LOGGER.error("sql filter creation has failed", e);
//			}
//		}
//		return sqlQueryResult;
//	}

	public JpaQueryResult getJpaQueryResult(CriteriaBuilder cb, Root<?> r, CriteriaQuery<?> cq, ObjectMapper objectMapper) {
		JpaQueryResult jpaQueryResult = null;
		if (hasFiltering()) {
			JpaQueryBuilderFactory jpaQueryBuilderFactory = new JpaQueryBuilderFactory(cb,r, cq, objectMapper);
			JpaBuilder jpaBuilder = jpaQueryBuilderFactory.builder();
			try {
				jpaQueryResult = jpaBuilder.build(filter);
			} catch (Exception e) {
				LOGGER.error("sql filter creation has failed", e);
			}
		}
		return jpaQueryResult;
	}

//	private void removeFilter(SqlQueryBuilderFactory sqlQueryBuilderFactory, Class<? extends IRuleFilter> clazz) {
//		for (IRuleFilter iRuleFilter : sqlQueryBuilderFactory.getFilters()) {
//        	if (iRuleFilter.getClass().isAssignableFrom(clazz)) {
//        		sqlQueryBuilderFactory.getFilters().remove(iRuleFilter);
//        		break;
//        	}
//        }
//	}
//	private void addUnescapeFieldFilter(String alias, SqlQueryBuilderFactory sqlQueryBuilderFactory) {
//		String prefix = alias + ".";
//		sqlQueryBuilderFactory.addFilter((JsonRule jsonRule, EnumBuilderType type) -> {
//			if (!jsonRule.isGroup()) {
//				IRule rule = jsonRule.toRule();
//				if (rule.getValue() != null) {
//					rule.setField(prefix + rule.getField().replace('_', '.'));
//				}
//			}
//		});
//	}

	public boolean hasFiltering() {
		return filter != null && !filter.equals("");
	}

	public boolean hasGlobalSearch() {
		return getFilterBy().getGlobalSearch() != null &&
				!"".equals(getFilterBy().getGlobalSearch());
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
