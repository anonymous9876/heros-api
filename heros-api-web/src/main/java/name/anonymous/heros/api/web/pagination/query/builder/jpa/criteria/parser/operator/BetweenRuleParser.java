package name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.operator;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.model.IRule;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.model.enums.EnumOperator;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.AbstractJpaRuleParser;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.JsonRuleParser;

public class BetweenRuleParser extends AbstractJpaRuleParser {
    public BetweenRuleParser(CriteriaBuilder cb, Root<?> r) {
		super(cb, r);
	}

	public boolean canParse(IRule rule) {
    	return EnumOperator.BETWEEN.equals(rule.getOperator()) ||
        		EnumOperator.NOT_BETWEEN.equals(rule.getOperator());
    }

    public Predicate parse(IRule rule, JsonRuleParser parser) {
    	List<Object> values = (List<Object>) rule.getValue();
    	Predicate predicate = getBetweenPredicate(rule.getField(), values.get(0), values.get(1));

    	if (EnumOperator.NOT_BETWEEN.equals(rule.getOperator())) {
    		predicate = cb.not(predicate);
    	}
    	return predicate;
    }

    private <Y extends Comparable<? super Y>> Predicate getBetweenPredicate(String pathString, Object value1Object, Object value2Object) {
    	Path<? extends Y> path = getPath(r, pathString);
    	Expression<? extends Y> value1 = cb.literal(path.getJavaType().cast(convert(value1Object, path.getJavaType())));
    	Expression<? extends Y> value2 = cb.literal(path.getJavaType().cast(convert(value2Object, path.getJavaType())));
		return cb.between(path, value1, value2);
    }
}
