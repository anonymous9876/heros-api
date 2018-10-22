package name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.model;

import java.util.List;

public interface IGroup {
    /**
     * Getter method for property <tt>condition</tt>.
     * @return property value of condition
     *
     */
    String getCondition();

    /**
     * Setter method for property <tt>condition</tt>.
     * @param condition value to be assigned to property condition
     *
     */
    void setCondition(String condition);

    /**
     * Getter method for property <tt>not</tt>.
     * @return property value of not
     *
     */
    Boolean getNot();

    /**
     * Setter method for property <tt>not</tt>.
     * @param not value to be assigned to property not
     *
     */
    void setNot(Boolean not);

    /**
     * Getter method for property <tt>rules</tt>.
     * @return property value of rules
     *
     */
    List<JsonRule> getRules();

    /**
     * Setter method for property <tt>rules</tt>.
     * @param rules value to be assigned to property rules
     *
     */
    void setRules(List<JsonRule> rules);

    /**
     * Getter method for property <tt>data</tt>.
     * @return property value of value
     *
     */
    Object getData();

    /**
     * Setter method for property <tt>data</tt>.
     * @param value value to be assigned to property value
     *
     */
    void setData(Object value);
}
