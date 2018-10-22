package name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.operator;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.UUID;

import javax.persistence.OneToMany;
import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.persistence.metamodel.SingularAttribute;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import name.anonymous.heros.api.web.pagination.query.builder.JpaQueryBuilderFactory;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.model.IRule;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.model.JsonRule;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.model.enums.EnumOperator;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.model.result.JpaQueryResult;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.AbstractJpaRuleParser;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.JsonRuleParser;

public class ExistsRuleParser extends AbstractJpaRuleParser {
	private AbstractQuery<?> cq;
	private ObjectMapper objectMapper;

	public ExistsRuleParser(CriteriaBuilder cb, Root<?> r, AbstractQuery<?> cq, ObjectMapper objectMapper) {
		super(cb, r);
		this.cq = cq;
		this.objectMapper = objectMapper;
	}

	public boolean canParse(IRule rule) {
		return EnumOperator.EXISTS.equals(rule.getOperator()) || EnumOperator.NOT_EXISTS.equals(rule.getOperator());
	}

	public Predicate parse(IRule rule, JsonRuleParser parser) {
		Class<?> collectionClass = getPath(r, rule.getField()).getParentPath().getJavaType();
		JsonRule jsonRule;
		try {
			jsonRule = (JsonRule) objectMapper.treeToValue(objectMapper.valueToTree(rule.getValue()), JsonRule.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		Class<?> subQueryClass = getFieldGenericType(collectionClass, rule.getField());
		Subquery<Integer> subQuery = cq.subquery(Integer.class);
		Root<?> subQueryRoot = subQuery.from(subQueryClass);

		JpaQueryBuilderFactory jpaQueryBuilderFactory = new JpaQueryBuilderFactory(cb, subQueryRoot, subQuery,
				objectMapper);
		JpaQueryResult jpaQueryResult = jpaQueryBuilderFactory.builder().build(jsonRule);

		subQuery.select(cb.literal(1));
		Predicate where = cb.conjunction();
//		Expression<?> subQueryPrimaryKey = subQueryRoot.get(subQueryRoot.getModel().getId(UUID.class));
//		Expression<?> foreignKey = getPath(r, rule.getField());
		Expression<?> rootPrimaryKey = r.get(r.getModel().getId(UUID.class));

		Expression<?> subQueryForeignKey = subQueryRoot.get(subQueryRoot.getModel()
				.getSingularAttribute(getMappedBy(collectionClass, rule.getField()), collectionClass));
		where.getExpressions().add(cb.equal(rootPrimaryKey, subQueryForeignKey));// UUID.fromString("00000000-0000-0000-0000-000000000001")
		where.getExpressions().add(jpaQueryResult.getPredicate());
		Predicate predicate = cb.exists(subQuery.where(where));
		if (EnumOperator.NOT_EXISTS.equals(rule.getOperator())) {
			predicate = cb.not(predicate);
		}
		return predicate;
	}

	private Class<?> getFieldGenericType(Class<?> clazz, String fieldString) {
		Field field;
		try {
			field = clazz.getDeclaredField(fieldString);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		Type type = field.getGenericType();
		if (type instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) type;
			Class<?> foreignClass = (Class<?>) pt.getActualTypeArguments()[0];
			return foreignClass;
		}
		return null;
	}

	private String getMappedBy(Class<?> clazz, String fieldString) {
		Field field;
		try {
			field = clazz.getDeclaredField(fieldString);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return field.getAnnotation(OneToMany.class).mappedBy();
	}
}
