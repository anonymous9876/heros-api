package name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.filter;

import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.exception.FilterException;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.model.JsonRule;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.model.enums.EnumBuilderType;

public interface IRuleFilter {
    void doFilter(JsonRule jsonRule, EnumBuilderType type) throws FilterException;
}
