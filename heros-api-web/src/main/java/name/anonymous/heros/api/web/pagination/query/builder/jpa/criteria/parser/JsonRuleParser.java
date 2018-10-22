package name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser;


import java.util.List;

import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.exception.ParserNotFoundException;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.filter.IRuleFilter;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.model.JsonRule;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.model.enums.EnumBuilderType;

public class JsonRuleParser {
    private EnumBuilderType builderType;    // builder type
    private IGroupParser groupParser;   // group parser
    private List<IRuleParser> ruleParsers;  // rule ruleParsers
    private List<IRuleFilter> ruleFilters;  // rule filter

    /**
     *
     * @param groupParser
     * @param ruleParsers
     */
    public JsonRuleParser(EnumBuilderType builderType, IGroupParser groupParser, List<IRuleParser> ruleParsers, List<IRuleFilter> ruleFilters) {
        this.builderType = builderType;
    	this.groupParser = groupParser;
        this.ruleParsers = ruleParsers;
        this.ruleFilters = ruleFilters;
    }

    /**
     *
     * @param jsonRule
     * @return
     * @throws Exception
     */
    public Object parse(JsonRule jsonRule) {
        // filter
        for (IRuleFilter ruleFilter : ruleFilters) {
            ruleFilter.doFilter(jsonRule, builderType);
        }

        // parse
        if (jsonRule.isGroup()) {
            return groupParser.parse(jsonRule, this);
        } else {
            for (IRuleParser ruleParser : ruleParsers) {
                if (ruleParser.canParse(jsonRule)) {
                    return ruleParser.parse(jsonRule, this);
                }
            }

            throw new ParserNotFoundException("Can't found rule parser for:" + jsonRule);
        }
    }
}
