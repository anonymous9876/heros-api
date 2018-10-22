package name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser;

import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.model.IRule;

public interface IRuleParser {

    boolean canParse(IRule rule);

    Object parse(IRule rule, JsonRuleParser parser);
}
