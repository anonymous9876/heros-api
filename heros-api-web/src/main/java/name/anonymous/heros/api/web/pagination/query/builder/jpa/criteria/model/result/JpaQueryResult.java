package name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.model.result;

import javax.persistence.criteria.Predicate;

public class JpaQueryResult extends AbstractResult {
    private Predicate predicate;

    /**
     * @param queryJson
     * @param predicate
     */
    public JpaQueryResult(String queryJson, Predicate predicate) {
        this.queryJson = queryJson;
        this.predicate = predicate;
    }

    /**
     * Getter method for property <tt>query</tt>.
     * @return property value of query
     */
    @Override
    public String getQuery() {
        return null;
    }

    public Predicate getPredicate() {
    	return predicate;
    }

    /**
     * @param withParams
     * @return
     */
    public Predicate getQuery(boolean withParams) {
        return null;
    }

    /**
     * to string
     * @return
     */
    @Override
    public String toString() {
        return queryJson;
    }
}
