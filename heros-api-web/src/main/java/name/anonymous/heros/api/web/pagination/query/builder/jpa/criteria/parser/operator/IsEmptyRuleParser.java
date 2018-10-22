package name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.operator;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.model.IRule;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.model.enums.EnumOperator;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.AbstractJpaRuleParser;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.JsonRuleParser;

public class IsEmptyRuleParser extends AbstractJpaRuleParser {
    public IsEmptyRuleParser(CriteriaBuilder cb, Root<?> r) {
		super(cb, r);
	}

	public boolean canParse(IRule rule) {
    	return  EnumOperator.IS_EMPTY.equals(rule.getOperator()) &&
    			EnumOperator.IS_NOT_EMPTY.equals(rule.getOperator());
    }

    public Predicate parse(IRule rule, JsonRuleParser parser) {
    	Predicate predicate = cb.isEmpty(getPath(r, rule.getField()));
    	if (EnumOperator.IS_NOT_EMPTY.equals(rule.getOperator())) {
    		predicate = cb.not(predicate);
    	}
    	return predicate;
    }
}
