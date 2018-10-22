package name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.operator;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.model.IRule;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.model.enums.EnumOperator;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.AbstractJpaRuleParser;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.JsonRuleParser;

public class GreaterRuleParser extends AbstractJpaRuleParser {
    public GreaterRuleParser(CriteriaBuilder cb, Root<?> r) {
		super(cb, r);
	}

	public boolean canParse(IRule rule) {
        return EnumOperator.GREATER.equals(rule.getOperator());
    }

    public Predicate parse(IRule rule, JsonRuleParser parser) {
    	Object value = rule.getValue();
    	if (value instanceof List) {
    		value = ((List<?>) value).get(0);
    	}
		return getGreaterPredicate(rule.getField(), value);
    }

    private <Y extends Comparable<? super Y>> Predicate getGreaterPredicate(String pathString, Object valueObject) {
    	Expression<? extends Y> path = getPath(r, pathString);
    	Expression<? extends Y> value = cb.literal(path.getJavaType().cast(convert(valueObject, path.getJavaType())));
		return cb.greaterThan(path, value);
    }
}
