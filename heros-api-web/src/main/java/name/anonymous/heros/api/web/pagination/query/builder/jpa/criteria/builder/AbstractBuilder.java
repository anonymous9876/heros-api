package name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.builder;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.exception.ParserNotFoundException;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.filter.IRuleFilter;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.model.JsonRule;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.model.enums.EnumBuilderType;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.IGroupParser;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.IRuleParser;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.JsonRuleParser;

public abstract class AbstractBuilder {
    private static ObjectMapper mapper; // object mapper
    private IGroupParser groupParser;   // group parser
    private List<IRuleParser> ruleParsers;  // rule ruleParsers
    private List<IRuleFilter> ruleFilters;  // rule filters

    static {
        // object mapper
        mapper = new ObjectMapper();

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * @param groupParser
     * @param ruleParsers
     * @param ruleFilters
     */
    public AbstractBuilder(IGroupParser groupParser, List<IRuleParser> ruleParsers, List<IRuleFilter> ruleFilters) {
        this.groupParser = groupParser;
        this.ruleParsers = ruleParsers;
        this.ruleFilters = ruleFilters;
    }

    /**
     * @param query
     * @return
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     * @throws Exception
     */
    protected Object parse(String query) throws JsonParseException, JsonMappingException, IOException {
        JsonRule jsonRule = mapper.readValue(query, JsonRule.class);
        return parse(jsonRule);
    }

    protected Object parse(JsonRule jsonRule) {
        // json rule parse
        return new JsonRuleParser(getBuilderType(), groupParser, ruleParsers, ruleFilters).parse(jsonRule);
    }

    /**
     * @param query
     * @return
     */
    public abstract Object build(String query) throws IOException, ParserNotFoundException;

    /**
     * get builder type
     * @return
     */
    protected abstract EnumBuilderType getBuilderType();
}
