package com.lexalytics.semantria.client;

import feign.codec.EncodeException;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.ArrayList;

/**
 * Based off the default QueryMapeEncoder and improved-
 *   - Skip static members
 *   - If JsonProperty annotation is found, use that for the field name
 */
public class QueryMapEncoder implements feign.QueryMapEncoder {
    private final Map<Class<?>, ObjectParamMetadata> classToMetadata =
            new HashMap<>();

    @Override
    public Map<String, Object> encode(Object object) throws EncodeException {
        try {
            ObjectParamMetadata metadata = getMetadata(object.getClass());
            Map<String, Object> fieldNameToValue = new HashMap<>();
            for (Field field : metadata.objectFields) {
                if (Modifier.isStatic(field.getModifiers())) {
                    // skip static
                    continue;
                }
                Object value = field.get(object);
                String fieldName = field.getName();
                // if has JsonProperty annotation, then use that as the fieldName
                if (field.isAnnotationPresent(JsonProperty.class)) {
                    JsonProperty annotation = field.getAnnotation(JsonProperty.class);
                    fieldName = annotation.value();
                }
                if (value != null && value != object) {
                    fieldNameToValue.put(fieldName, value);
                }
            }
            return fieldNameToValue;
        } catch (IllegalAccessException e) {
            throw new EncodeException("Failure encoding object into query map", e);
        }
    }

    private ObjectParamMetadata getMetadata(Class<?> objectType) {
        ObjectParamMetadata metadata = classToMetadata.get(objectType);
        if (metadata == null) {
            metadata = ObjectParamMetadata.parseObjectType(objectType);
            classToMetadata.put(objectType, metadata);
        }
        return metadata;
    }

    private static class ObjectParamMetadata {

        private final List<Field> objectFields;

        private ObjectParamMetadata(List<Field> objectFields) {
            this.objectFields = Collections.unmodifiableList(objectFields);
        }

        private static ObjectParamMetadata parseObjectType(Class<?> type) {
            List<Field> fields = new ArrayList<>();
            for (Field field : type.getDeclaredFields()) {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                fields.add(field);
            }
            return new ObjectParamMetadata(fields);
        }
    }
}
