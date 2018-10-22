package name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.model;

public interface IRule {
    /**
     * Getter method for property <tt>id</tt>.
     * @return property value of id
     *
     */
    String getId();

    /**
     * Setter method for property <tt>id</tt>.
     * @param id value to be assigned to property id
     *
     */
    void setId(String id);

    /**
     * Getter method for property <tt>field</tt>.
     * @return property value of field
     *
     */
    String getField();

    /**
     * Setter method for property <tt>field</tt>.
     * @param field value to be assigned to property field
     *
     */
    void setField(String field);

    /**
     * Getter method for property <tt>type</tt>.
     * @return property value of type
     *
     */
    String getType();

    /**
     * Setter method for property <tt>type</tt>.
     * @param type value to be assigned to property type
     *
     */
    void setType(String type);

    /**
     * Getter method for property <tt>input</tt>.
     * @return property value of input
     *
     */
    String getInput();

    /**
     * Setter method for property <tt>input</tt>.
     * @param input value to be assigned to property input
     *
     */
    void setInput(String input);

    /**
     * Getter method for property <tt>operator</tt>.
     * @return property value of operator
     *
     */
    String getOperator();

    /**
     * Setter method for property <tt>operator</tt>.
     * @param operator value to be assigned to property operator
     *
     */
    void setOperator(String operator);

    /**
     * Getter method for property <tt>value</tt>.
     * @return property value of value
     *
     */
    Object getValue();

    /**
     * Setter method for property <tt>value</tt>.
     * @param value value to be assigned to property value
     *
     */
    void setValue(Object value);

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
