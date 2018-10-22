package name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.operator;

import java.util.Collection;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.model.IRule;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.model.enums.EnumOperator;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.AbstractJpaRuleParser;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.JsonRuleParser;

public class INRuleParser extends AbstractJpaRuleParser {
    public INRuleParser(CriteriaBuilder cb, Root<?> r) {
		super(cb, r);
	}

	public boolean canParse(IRule rule) {
    	return EnumOperator.IN.equals(rule.getOperator()) ||
    			EnumOperator.NOT_IN.equals(rule.getOperator());
    }

    public Predicate parse(IRule rule, JsonRuleParser parser) {
    	Predicate predicate = getPath(r, rule.getField()).in((Collection<?>) rule.getValue());
    	if (EnumOperator.NOT_IN.equals(rule.getOperator())) {
    		predicate = cb.not(predicate);
    	}
    	return predicate;
    }
}
