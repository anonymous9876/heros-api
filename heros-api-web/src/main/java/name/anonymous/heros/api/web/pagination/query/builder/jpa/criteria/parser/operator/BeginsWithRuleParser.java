package name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.operator;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.criterion.MatchMode;

import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.model.IRule;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.model.enums.EnumOperator;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.AbstractJpaRuleParser;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.JsonRuleParser;

public class BeginsWithRuleParser extends AbstractJpaRuleParser {
    public BeginsWithRuleParser(CriteriaBuilder cb, Root<?> r) {
		super(cb, r);
	}

	public boolean canParse(IRule rule) {
    	return EnumOperator.BEGINS_WITH.equals(rule.getOperator()) ||
        		EnumOperator.NOT_BEGINS_WITH.equals(rule.getOperator());
    }

    public Predicate parse(IRule rule, JsonRuleParser parser) {
    	Predicate predicate = cb.like(getPath(r, rule.getField()), MatchMode.START.toMatchString(rule.getValue().toString()));
    	if (EnumOperator.NOT_BEGINS_WITH.equals(rule.getOperator())) {
    		predicate = cb.not(predicate);
    	}
    	return predicate;
    }
}
