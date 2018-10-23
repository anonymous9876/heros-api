package name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser;

import java.time.LocalDate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.springframework.util.NumberUtils;

public abstract class AbstractJpaRuleParser implements IRuleParser {
	protected CriteriaBuilder cb;
	protected Root<?> r;

	public AbstractJpaRuleParser(CriteriaBuilder cb, Root<?> r) {
		super();
		this.cb = cb;
		this.r = r;
	}

	/**
	 * Spécifiez le chemin pour le nom donné (peut inclure des points pour parcourir
	 * les objets).
	 *
	 * @param base basis
	 * @param name name
	 * @param <T> attribut type
	 * @return path
	 */
	public static <T> Path<T> getPath(final Path<?> base, final String name) {
		final Path<T> result;
		final int index = name.indexOf('.');
		if (index == -1) {
			result = base.get(name);
		} else {
			final String part = name.substring(0, index);
			final String rest = name.substring(index + 1);

			final Path<?> partPath = base.get(part);
			if (partPath.getModel() == null) {
				// Ensuite, nous ne pouvons pas passer par cela, mais nous devons passer par une
				// jointure
				final Join<?, ?> join = ((From<?, ?>) base).join(part);
				result = getPath(join, rest);
			} else {
				result = getPath(partPath, rest);
			}
		}
		return result;
	}

	protected Object convert(Object value, Class<?> clazz) {
        if (clazz.isAssignableFrom(String.class)) {
            if (!(value instanceof String)) {
                return String.valueOf(value);
            }
        } else if (clazz.isAssignableFrom(Double.class)) {
            if (!(value instanceof Double)) {
                if (value instanceof Number) {
                    return NumberUtils.convertNumberToTargetClass((Number) value, Double.class);
                } else {
                    return NumberUtils.parseNumber(value.toString(), Double.class);
                }
            }
        } else if (clazz.isAssignableFrom(Integer.class)) {
            if (!(value instanceof Integer)) {
                if (value instanceof Number) {
                    return NumberUtils.convertNumberToTargetClass((Number) value, Integer.class);
                } else {
                    return NumberUtils.parseNumber(value.toString(), Integer.class);
                }
            }
        } else if (clazz.isAssignableFrom(LocalDate.class)) {
        	if (!(value instanceof LocalDate)) {
                return LocalDate.parse(value.toString());
            }
        }
        return value;
    }
}
