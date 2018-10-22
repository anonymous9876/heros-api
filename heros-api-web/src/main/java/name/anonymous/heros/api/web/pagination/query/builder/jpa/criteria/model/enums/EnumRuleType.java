package name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.model.enums;

public enum EnumRuleType {
    STRING("string"),
    INTEGER("integer"),
    DOUBLE("double"),
    DATE("date"),
    TIME("time"),
    DATETIME("datetime"),
    BOOLEAN("boolean");

    private final String value;

    /**
     *
     * @param value
     */
    EnumRuleType(String value) {
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
    public boolean equals(String value) {
        if (value == null) {
            return false;
        }
        return this.value.equals(value);
    }
}
