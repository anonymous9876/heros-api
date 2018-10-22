package name.anonymous.heros.api.web.pagination.rest.util.hibernate;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;

import com.itfsw.query.builder.support.model.result.SqlQueryResult;

import name.anonymous.heros.api.web.pagination.rest.RestPaginationCriteria;
import name.anonymous.heros.api.web.pagination.rest.SortOrder;

@Service
public class HibernatePaginationService {
//	private static final Logger LOGGER = LoggerFactory.getLogger(HibernatePaginationService.class);

	public String getOrderBy(String alias, RestPaginationCriteria restPaginationCriteria) {
		if (restPaginationCriteria.getSortBy().getSortBys().isEmpty()) {
			return "";
		}
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(" ORDER BY ");
		for (Entry<String, SortOrder> entry : restPaginationCriteria.getSortBy().getSortBys().entrySet()) {
			String column = entry.getKey();
			String sortingDirection = entry.getValue().toString();
			stringBuilder.append("".equals(alias) ? column : alias + "." + column).append(" ").append(sortingDirection).append(", ");
		}
		return stringBuilder.substring(0, stringBuilder.length() - 2) + " ";
	}

	public String formatWhere(String whereContent) {
		if (whereContent == null || "".equals(whereContent)) {
			return "";
		} else {
			return " WHERE " + whereContent + " ";
		}
	}

	private boolean isEmpty(String string) {
		return string == null || "".equals(string);
	}

	private String concatConditions(String operator, String condition1, String condition2) {
		if (isEmpty(condition1) && isEmpty(condition2)) {
			return "";
		} else if (isEmpty(condition1)) {
			return condition2;
		} else if (isEmpty(condition2)) {
			return condition1;
		} else {
			return condition1 + " " + operator + " " + condition2;
		}
	}

	public String concatConditions(String operator, String... conditions) {
		String condition = "";
		for (int i = 0; i < conditions.length; i++) {
			condition = concatConditions(operator, condition, conditions[i]);
		}
		return condition;
	}

	public String and(String... conditions) {
		return concatConditions("AND", conditions);
	}

	public String or(String... conditions) {
		return concatConditions("OR", conditions);
	}

	public String group(String condition) {
		if (isEmpty(condition)) {
			return "";
		} else {
			return "(" + condition + ")";
		}
	}

//	public String getWhereQuery(String alias, RestPaginationCriteria restPaginationCriteria) {
//		return getWhereQuery(alias, restPaginationCriteria.getSqlQueryResult(alias));
//	}

	public String getWhereQuery(String alias, SqlQueryResult sqlQueryResult) {
		if (sqlQueryResult == null) {
			return "";
		}
		String whereQuery = sqlQueryResult.getQuery();
		if (whereQuery != null && !"".equals(whereQuery)) {
			whereQuery = positionalParametersToHibernateNamedParameters(whereQuery, alias);
			return group(whereQuery);
		}
		return "";
	}

	private String positionalParametersToHibernateNamedParameters(String query, String prefix) {
		StringBuilder sb = new StringBuilder();
		int count = 0;
		for (int i = 0; i < query.length(); i++) {
			char c = query.charAt(i);
			if (c == '?') {
				sb
						.append(":")
						.append(prefix)
						.append(count++);
			} else {
				sb.append(query.charAt(i));
			}
		}
		return sb.toString();
	}

//	public void addWhereParams(String alias, RestPaginationCriteria restPaginationCriteria, TypedQuery<?> typedQuery) {
//		SqlQueryResult sqlQueryResult = restPaginationCriteria.getSqlQueryResult(alias);
//		if (sqlQueryResult == null) {
//			return ;
//		}
//		List<Object> params = sqlQueryResult.getParams();
//		for (int i = 0; i < params.size(); i++) {
//			String ParamPrefix = alias + Integer.toString(i);
//			Object o = params.get(i);
//			if (typedQuery.getParameter(ParamPrefix).getParameterType() != null
//					&& typedQuery.getParameter(ParamPrefix).getParameterType().isAssignableFrom(LocalDate.class)) {
//				typedQuery.setParameter(ParamPrefix, LocalDate.parse(o.toString()));
//			} else {
//				typedQuery.setParameter(ParamPrefix, o);
//			}
//		}
//	}


	public String getGlobalSearchQuery(String alias, RestPaginationCriteria restPaginationCriteria, Collection<String> entityProperties) {
		if (!restPaginationCriteria.hasGlobalSearch()) {
			return "";
		}

		StringBuilder stringBuilder = new StringBuilder(" ( ");
		for (String entityProperty : entityProperties) {
			stringBuilder.append("cast(");
			if (!"".equals(alias)) {
				stringBuilder.append(alias).append(".");
			}
			stringBuilder.append(entityProperty).append(" as string)").append(" like :globalSearch").append(" OR ");
		}
		return stringBuilder.substring(0, stringBuilder.length() - 4) + " ) ";
	}

	public void addGlobalSearchParams(RestPaginationCriteria restPaginationCriteria, TypedQuery<?> typedQuery) {
		if (!restPaginationCriteria.hasGlobalSearch()) {
			return;
		}
		typedQuery.setParameter("globalSearch", "%" + restPaginationCriteria.getFilterBy().getGlobalSearch() + "%");
	}
}
