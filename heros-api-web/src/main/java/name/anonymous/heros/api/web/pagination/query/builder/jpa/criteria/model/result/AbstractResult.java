package name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.model.result;

public abstract class AbstractResult {
    protected String queryJson;
    /**
     * @return
     */
    public abstract Object getQuery();

    /**
     * to String
     * @return
     */
    public abstract String toString();

    /**
     * Getter method for property <tt>queryJson</tt>.
     * @return property value of queryJson
     */
    public String getQueryJson() {
        return queryJson;
    }
}
