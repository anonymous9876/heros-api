package name.anonymous.heros.api.web.pagination.query.builder;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;

import com.fasterxml.jackson.databind.ObjectMapper;

import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.builder.JpaBuilder;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.AbstractJpaRuleParser;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.IRuleParser;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.operator.BeginsWithRuleParser;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.operator.BetweenRuleParser;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.operator.ContainsRuleParser;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.operator.DefaultGroupParser;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.operator.EndsWithRuleParser;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.operator.EqualRuleParser;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.operator.ExistsRuleParser;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.operator.GreaterOrEqualRuleParser;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.operator.GreaterRuleParser;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.operator.INRuleParser;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.operator.IsEmptyRuleParser;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.operator.IsNullRuleParser;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.operator.LessOrEqualRuleParser;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.operator.LessRuleParser;

public class JpaQueryBuilderFactory extends AbstractQueryBuilderFactory {
    /**
     * @param config
     */
    public JpaQueryBuilderFactory(CriteriaBuilder cb, Root<?> r, AbstractQuery<?> cq, ObjectMapper objectMapper) {
        super();

        // ------------------------ filter ---------------------------

        // ------------------------- group parser ----------------------
        groupParser = new DefaultGroupParser(cb);

        // ---------------------- rule parser ----------------------------
        ruleParsers.add(new EqualRuleParser(cb, r));
        ruleParsers.add(new INRuleParser(cb, r));
        ruleParsers.add(new LessRuleParser(cb, r));
        ruleParsers.add(new LessOrEqualRuleParser(cb, r));
        ruleParsers.add(new GreaterRuleParser(cb, r));
        ruleParsers.add(new GreaterOrEqualRuleParser(cb, r));
        ruleParsers.add(new BetweenRuleParser(cb, r));
        ruleParsers.add(new BeginsWithRuleParser(cb, r));
        ruleParsers.add(new ContainsRuleParser(cb, r));
        ruleParsers.add(new EndsWithRuleParser(cb, r));
        ruleParsers.add(new IsEmptyRuleParser(cb, r));
        ruleParsers.add(new IsNullRuleParser(cb, r));

        ruleParsers.add(new ExistsRuleParser(cb, r, cq, objectMapper));
    }

    public JpaBuilder builder() {
        List<IRuleParser> JpaRuleParsers = new ArrayList<>();
        for (IRuleParser parser : ruleParsers) {
            if (parser instanceof AbstractJpaRuleParser) {
                JpaRuleParsers.add(parser);
            }
        }
        return new JpaBuilder(groupParser, JpaRuleParsers, filters);
    }
}
