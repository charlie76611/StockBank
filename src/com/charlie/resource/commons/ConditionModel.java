package com.charlie.resource.commons;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

@SuppressWarnings("serial")
public class ConditionModel implements Serializable {


    private ConditionPrefix conditionPrefix; // AND / OR
    private ConditionType conditionType; // LIKE, =, <>, >=, <=, IN, BETWEEN
    private String conditionKey;
    private String conditionValue;

    @Override
    public String toString() {
        return new ToStringBuilder(this).appendSuper(super.toString()).append(
                "conditionPrefix", conditionPrefix).append("conditionType",
                conditionType).append("conditionKey", conditionKey).append(
                "conditionValue", conditionValue).toString();
    }

    public ConditionModel() {
    }

    public ConditionModel(ConditionPrefix conditionPrefix, String conditionKey,
            ConditionType conditionType, String conditionValue) {
        super();
        this.conditionPrefix = conditionPrefix;
        this.conditionType = conditionType;
        this.conditionKey = conditionKey;
        this.conditionValue = conditionValue;
    }


    public String getConditionPrefix() {
        if ( conditionPrefix == null ) {
            conditionPrefix = ConditionPrefix.NONE;
        }
        return conditionPrefix.sql();
    }

    public void setConditionPrefix(ConditionPrefix conditionPrefix) {
        this.conditionPrefix = conditionPrefix;
    }
    
    public String getConditionType() {
        if ( conditionType == null ) {
            conditionType = ConditionType.NONE;
        }
        return conditionType.sql();
    }

    public void setConditionType(ConditionType conditionType) {
        this.conditionType = conditionType;
    }

    public String getConditionKey() {
        return conditionKey;
    }

    public void setConditionKey(String conditionKey) {
        this.conditionKey = conditionKey;
    }

    public String getConditionValue() {
        return conditionValue;
    }

    public void setConditionValue(String conditionValue) {
        this.conditionValue = conditionValue;
    }

    public static enum ConditionPrefix {
        AND, OR, NONE;
        public String sql() {
            String result = "";
            switch (this) {
                case AND:
                    result = " AND ";
                    break;
                case OR:
                    result = " OR ";
                    break;
                case NONE:
                    result = "";
                    break;
                default:
                    result = "";
            }
            return result;
        }
    }

    public static enum ConditionType {
        NONE, LIKE, EQ, NOT_EQ, LARGER_EQ, SMALLER_EQ, IN, BETWEEN;
        public String sql() {
            String result = "";
            switch (this) {
                case NONE:
                    result = "";
                    break;
                case LIKE:
                    result = " LIKE ";
                    break;
                case EQ:
                    result = " = ";
                    break;
                case NOT_EQ:
                    result = " <> ";
                    break;
                case LARGER_EQ:
                    result = " >= ";
                    break;
                case SMALLER_EQ:
                    result = " <= ";
                    break;
                case IN:
                    result = " IN ";
                    break;
                case BETWEEN:
                    result = " BETWEEN ";
                    break;
                default:
                    result = "";
            }
            return result;
        }
    }
}
