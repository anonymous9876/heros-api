package name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.operator;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;

import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.model.IGroup;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.model.enums.EnumCondition;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.IGroupParser;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.JsonRuleParser;

public class DefaultGroupParser implements IGroupParser {
	private CriteriaBuilder cb;

	public DefaultGroupParser(CriteriaBuilder cb) {
		super();
		this.cb = cb;
	}

	@Override
	public Predicate parse(IGroup group, JsonRuleParser parser) {
		Predicate predicate;

		if (EnumCondition.AND.equals(group.getCondition())) {
			predicate = cb.conjunction();
		} else if (EnumCondition.OR.equals(group.getCondition())) {
			predicate = cb.disjunction();
		} else {
			throw new RuntimeException("unknowed condition");
		}
		// rules
		for (int i = 0; i < group.getRules().size(); i++) {
			// json parse
			Predicate childPredicate = (Predicate) parser.parse(group.getRules().get(i));
			predicate.getExpressions().add(childPredicate);
		}

		// NOT
		if (group.getNot() != null && group.getNot()) {
			cb.not(predicate);
		}
		return predicate;
	}
}
