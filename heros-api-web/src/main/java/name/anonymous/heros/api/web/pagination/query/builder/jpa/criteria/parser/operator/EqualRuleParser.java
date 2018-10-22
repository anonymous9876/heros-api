package name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.operator;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.model.IRule;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.model.enums.EnumOperator;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.AbstractJpaRuleParser;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.JsonRuleParser;

public class EqualRuleParser extends AbstractJpaRuleParser {
	public EqualRuleParser(CriteriaBuilder cb, Root<?> r) {
		super(cb, r);
	}

	public boolean canParse(IRule rule) {
        return EnumOperator.EQUAL.equals(rule.getOperator()) ||
        		EnumOperator.NOT_EQUAL.equals(rule.getOperator());
    }

    public Predicate parse(IRule rule, JsonRuleParser parser) {
    	Predicate predicate = cb.equal(getPath(r, rule.getField()), rule.getValue());
    	if (EnumOperator.NOT_EQUAL.equals(rule.getOperator())) {
    		predicate = cb.not(predicate);
    	}
    	return predicate;
    }
}
