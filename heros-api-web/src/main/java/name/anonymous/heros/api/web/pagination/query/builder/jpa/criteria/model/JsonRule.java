package name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.model;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonRule implements IGroup, IRule {
    // --------------------------- Rule --------------------------------------
    private String id;
    private String field;
    private String type;
    private String input;
    private String operator;
    private Object value;
    private Object data;
    // --------------------------- Group --------------------------------------
    private String condition;
    private Boolean not;
    private List<JsonRule> rules;

    /**
     * group
     * @return
     */
    public boolean isGroup() {
        return condition != null;
    }

    /**
     * group
     * @return
     */
    public IGroup toGroup() {
        return this;
    }

    /**
     * rule
     * @return
     */
    public IRule toRule() {
        return this;
    }

    /**
     * to String
     * @return
     */
    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (Exception e) {
            return super.toString();
        }
    }

    /**
     * Getter method for property <tt>id</tt>.
     * @return property value of id
     *
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * Setter method for property <tt>id</tt>.
     * @param id value to be assigned to property id
     *
     */
    @Override
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter method for property <tt>field</tt>.
     * @return property value of field
     *
     */
    @Override
    public String getField() {
        return field;
    }

    /**
     * Setter method for property <tt>field</tt>.
     * @param field value to be assigned to property field
     *
     */
    @Override
    public void setField(String field) {
        this.field = field;
    }

    /**
     * Getter method for property <tt>type</tt>.
     * @return property value of type
     *
     */
    @Override
    public String getType() {
        return type;
    }

    /**
     * Setter method for property <tt>type</tt>.
     * @param type value to be assigned to property type
     *
     */
    @Override
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter method for property <tt>input</tt>.
     * @return property value of input
     *
     */
    @Override
    public String getInput() {
        return input;
    }

    /**
     * Setter method for property <tt>input</tt>.
     * @param input value to be assigned to property input
     *
     */
    @Override
    public void setInput(String input) {
        this.input = input;
    }

    /**
     * Getter method for property <tt>operator</tt>.
     * @return property value of operator
     *
     */
    @Override
    public String getOperator() {
        return operator;
    }

    /**
     * Setter method for property <tt>operator</tt>.
     * @param operator value to be assigned to property operator
     *
     */
    @Override
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * Getter method for property <tt>value</tt>.
     * @return property value of value
     *
     */
    @Override
    public Object getValue() {
        return value;
    }

    /**
     * Setter method for property <tt>value</tt>.
     * @param value value to be assigned to property value
     *
     */
    @Override
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * Getter method for property <tt>data</tt>.
     * @return property value of data
     *
     */
    @Override
    public Object getData() {
        return data;
    }

    /**
     * Setter method for property <tt>data</tt>.
     * @param data value to be assigned to property data
     *
     */
    @Override
    public void setData(Object data) {
        this.data = data;
    }

    /**
     * Getter method for property <tt>condition</tt>.
     * @return property value of condition
     *
     */
    @Override
    public String getCondition() {
        return condition;
    }

    /**
     * Setter method for property <tt>condition</tt>.
     * @param condition value to be assigned to property condition
     *
     */
    @Override
    public void setCondition(String condition) {
        this.condition = condition;
    }

    /**
     * Getter method for property <tt>not</tt>.
     * @return property value of not
     *
     */
    @Override
    public Boolean getNot() {
        return not;
    }

    /**
     * Setter method for property <tt>not</tt>.
     * @param not value to be assigned to property not
     *
     */
    @Override
    public void setNot(Boolean not) {
        this.not = not;
    }

    /**
     * Getter method for property <tt>rules</tt>.
     * @return property value of rules
     *
     */
    @Override
    public List<JsonRule> getRules() {
        return rules;
    }

    /**
     * Setter method for property <tt>rules</tt>.
     * @param rules value to be assigned to property rules
     *
     */
    @Override
    public void setRules(List<JsonRule> rules) {
        this.rules = rules;
    }
}
