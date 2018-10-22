package name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.filter;


import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.exception.FilterException;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.model.IGroup;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.model.IRule;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.model.JsonRule;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.model.enums.EnumBuilderType;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.model.enums.EnumOperator;

public class ValidateFilter implements IRuleFilter {
    /**
     * @param jsonRule
     * @throws FilterException
     */
    public void doFilter(JsonRule jsonRule, EnumBuilderType type) throws FilterException {
        if (jsonRule.isGroup()) {
            IGroup group = jsonRule.toGroup();
            if (CollectionUtils.isEmpty(group.getRules())) {
                throw new FilterException("group's rules can not be empty for: " + group);
            }
        } else {
            IRule rule = jsonRule.toRule();
            // field
            if (StringUtils.isEmpty(rule.getField())) {
                throw new FilterException("rule's field can not be empty for:" + rule);
            }
            // operator
            if (StringUtils.isEmpty(rule.getOperator())) {
                throw new FilterException("rule's operator can not be empty for:" + rule);
            }
            // equal
            if (EnumOperator.EQUAL.equals(rule.getOperator())) {
                if (rule.getValue() instanceof List) {
                    List<Object> list = (List<Object>) rule.getValue();
                    if (list.size() > 1) {
                        throw new FilterException("Operator \"equal\" cannot accept multiple values for:" + rule);
                    }
                    rule.setValue(list.get(0));
                }
            }

            // must be list
            if (EnumOperator.IN.equals(rule.getOperator()) || EnumOperator.NOT_IN.equals(rule.getOperator())
                    || EnumOperator.BETWEEN.equals(rule.getOperator()) || EnumOperator.NOT_BETWEEN.equals(rule.getOperator())) {
                // list
                if (!(rule.getValue() instanceof List)) {
                    throw new FilterException("rule's value must be Array for:" + rule);
                }

                // size
                if (EnumOperator.BETWEEN.equals(rule.getOperator()) || EnumOperator.NOT_BETWEEN.equals(rule.getOperator())) {
                    List list = (List) rule.getValue();
                    if (list.size() != 2) {
                        throw new FilterException("rule's value size must be 2 for:" + rule);
                    }
                }
            }
        }
    }
}
