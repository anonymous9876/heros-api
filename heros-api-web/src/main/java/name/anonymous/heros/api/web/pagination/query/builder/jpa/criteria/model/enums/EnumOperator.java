package name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.model.enums;

public enum EnumOperator {
    EQUAL("equal"),
    NOT_EQUAL("not_equal"),
    IN("in"),
    NOT_IN("not_in"),
    LESS("less"),
    LESS_OR_EQUAL("less_or_equal"),
    GREATER("greater"),
    GREATER_OR_EQUAL("greater_or_equal"),
    BETWEEN("between"),
    NOT_BETWEEN("not_between"),
    BEGINS_WITH("begins_with"),
    NOT_BEGINS_WITH("not_begins_with"),
    CONTAINS("contains"),
    NOT_CONTAINS("not_contains"),
    ENDS_WITH("ends_with"),
    NOT_ENDS_WITH("not_ends_with"),
    IS_EMPTY("is_empty"),
    IS_NOT_EMPTY("is_not_empty"),
    IS_NULL("is_null"),
    IS_NOT_NULL("is_not_null"),

    EXISTS("exists"),
    NOT_EXISTS("not_exists");


    private final String value;

    /**
     *
     * @param value
     */
    EnumOperator(String value) {
        this.value = value;
    }

    /**
     * Getter method for property <tt>value</tt>.
     * @return property value of value
     *
     */
    public String getValue() {
        return value;
    }

    /**
     * Getter method for property <tt>value</tt>.
     * @return property value of value
     *
     */
    public String value() {
        return value;
    }

    /**
     *
     * @param value
     * @return
     */
    public boolean equals(String value){
        if (value == null){
            return false;
        }
        return this.value.equals(value);
    }
}
