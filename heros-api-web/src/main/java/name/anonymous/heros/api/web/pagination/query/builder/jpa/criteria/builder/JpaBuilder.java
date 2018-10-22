package name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.builder;

import java.io.IOException;
import java.util.List;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.filter.IRuleFilter;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.model.JsonRule;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.model.enums.EnumBuilderType;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.model.result.JpaQueryResult;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.IGroupParser;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.IRuleParser;

public class JpaBuilder extends AbstractBuilder {

    /**
     * @param groupParser
     * @param ruleParsers
     * @param ruleFilters
     */
    public JpaBuilder(IGroupParser groupParser, List<IRuleParser> ruleParsers, List<IRuleFilter> ruleFilters) {
        super(groupParser, ruleParsers, ruleFilters);
    }

    /**
     * @param query
     * @return
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     * @throws Exception
     */
    public JpaQueryResult build(String query) throws JsonParseException, JsonMappingException, IOException {
    	Predicate predicate = (Predicate) super.parse(query);
        return new JpaQueryResult(query, predicate);
    }

    public JpaQueryResult build(JsonRule jsonRule) {
    	Predicate predicate = (Predicate) super.parse(jsonRule);
        return new JpaQueryResult(jsonRule.toString(), predicate);
    }

    /**
     * get builder type
     * @return
     */
    @Override
    protected EnumBuilderType getBuilderType() {
        return EnumBuilderType.JPA;
    }
}
