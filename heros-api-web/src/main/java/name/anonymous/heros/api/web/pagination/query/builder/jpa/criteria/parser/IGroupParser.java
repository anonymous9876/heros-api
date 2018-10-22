package name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser;

import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.model.IGroup;

public interface IGroupParser {
    Object parse(IGroup group, JsonRuleParser parser);
}
