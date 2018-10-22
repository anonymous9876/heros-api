package name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.operator;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.model.IRule;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.model.enums.EnumOperator;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.AbstractJpaRuleParser;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.JsonRuleParser;

public class LessOrEqualRuleParser extends AbstractJpaRuleParser {
    public LessOrEqualRuleParser(CriteriaBuilder cb, Root<?> r) {
		super(cb, r);
	}

	public boolean canParse(IRule rule) {
        return EnumOperator.LESS_OR_EQUAL.equals(rule.getOperator());
    }

    public Predicate parse(IRule rule, JsonRuleParser parser) {
    	return getLessOrEqualPredicate(rule.getField(), rule.getValue());
    }

    private <Y extends Comparable<? super Y>> Predicate getLessOrEqualPredicate(String pathString, Object valueObject) {
    	Expression<? extends Y> path = getPath(r, pathString);
    	Expression<? extends Y> value = cb.literal(path.getJavaType().cast(convert(valueObject, path.getJavaType())));
		return cb.lessThanOrEqualTo(path, value);
    }
}
