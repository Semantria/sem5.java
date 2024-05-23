package com.lexalytics.semantria.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.node.*;
import com.lexalytics.semantria.client.SemantriaClientError;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MetadataValue {
    private ValueNode value;

    public MetadataValue() {
    }

    public MetadataValue(ValueNode value) {
        this.value = value;
    }

    public static MetadataValue of(String value) {
        return new MetadataValue(TextNode.valueOf(value));
    }

    public static MetadataValue of(Boolean value) {
        if (value == null) {
            return new MetadataValue(NullNode.getInstance());
        } else {
            return new MetadataValue(BooleanNode.valueOf(value));
        }
    }

    public static MetadataValue of(boolean value) {
        return new MetadataValue(BooleanNode.valueOf(value));
    }

    public static MetadataValue of(Long value) {
        return new MetadataValue(LongNode.valueOf(value));
    }

    public static MetadataValue of(long value) {
        return new MetadataValue(LongNode.valueOf(value));
    }

    public static MetadataValue of(Double value) {
        return new MetadataValue(DoubleNode.valueOf(value));
    }

    public static MetadataValue of(double value) {
        return new MetadataValue(DoubleNode.valueOf(value));
    }

    public Object getValue() {
        if (value == null) return null;
        if (value.isTextual()) return value.asText();
        if (value.isBoolean()) return value.asBoolean();
        if (value.isNumber()) return value.numberValue();
        throw new RuntimeException("Unexpected metadata value: " + value.toString());
    }

    public void setValue(Object value) {
        if (value == null) {
            this.value = null;
        } else if (value instanceof String) {
            this.value = TextNode.valueOf((String)value);
        } else if (value instanceof Integer) {
            this.value = LongNode.valueOf((Integer)value);
        } else if (value instanceof Long) {
            this.value = LongNode.valueOf((Long)value);
        } else if (value instanceof Float) {
            this.value = DoubleNode.valueOf((Float)value);
        } else if (value instanceof Double) {
            this.value = DoubleNode.valueOf((Double)value);
        } else if (value instanceof Boolean) {
            this.value = BooleanNode.valueOf((Boolean)value);
        } else if (value instanceof ValueNode) {
            this.value = (ValueNode) value;
        } else {
            throw new SemantriaClientError(String.format("Invalid type metadata value: %s",
                    value.getClass().getCanonicalName()));
        }
    }

    @Override
    public String toString() {
        return value.toString();
    }

}
