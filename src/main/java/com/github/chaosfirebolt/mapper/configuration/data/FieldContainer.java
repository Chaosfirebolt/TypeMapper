package com.github.chaosfirebolt.mapper.configuration.data;

import com.github.chaosfirebolt.mapper.configuration.access.Accessor;
import com.github.chaosfirebolt.mapper.configuration.access.AccessorFactory;
import com.github.chaosfirebolt.mapper.configuration.annotation.Collection;
import com.github.chaosfirebolt.mapper.exc.MappingException;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by ChaosFire on 15-May-18
 */
public final class FieldContainer {

    private final Map<String, Accessor> accessorMap;
    private final Map<String, Boolean> collections;
    private final Map<String, Field> fieldMap;

    private FieldContainer(Map<String, Accessor> accessorMap, Map<String, Boolean> collections, Map<String, Field> fieldMap) {
        this.accessorMap = accessorMap;
        this.collections = collections;
        this.fieldMap = fieldMap;
    }

    static FieldContainer parse(Class<?> clazz) {
        Map<String, Accessor> accessorMap = new LinkedHashMap<>();
        Map<String, Boolean> collections = new HashMap<>();
        Map<String, Field> fieldMap = new HashMap<>();
        traverse(clazz, accessorMap, collections, fieldMap);
        return new FieldContainer(accessorMap, collections, fieldMap);
    }

    private static void traverse(Class<?> clazz, Map<String, Accessor> accessorMap, Map<String, Boolean> collections, Map<String, Field> fieldMap) {
        if (clazz.equals(Object.class)) {
            return;
        }
        traverse(clazz.getSuperclass(), accessorMap, collections, fieldMap);
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Accessor accessor = AccessorFactory.createAccessor(field);
            if (accessor != null) {
                accessorMap.put(field.getName(), accessor);
                fieldMap.put(field.getName(), field);
                if (java.util.Collection.class.isAssignableFrom(field.getType())) {
                    Collection collection = field.getAnnotation(Collection.class);
                    if (collection == null) {
                        String message = String.format("@Collection annotation missing from field %s in class %s.", field.getName(), clazz.getName());
                        throw new MappingException(message);
                    }
                    collections.put(field.getName(), true);
                }
            }
        }
    }

    public Map<String, Accessor> accessorMap() {
        return Collections.unmodifiableMap(this.accessorMap);
    }

    public boolean isCollection(String fieldName) {
        Boolean result = this.collections.get(fieldName);
        if (result == null) {
            return false;
        }
        return result;
    }

    public Field field(String fieldName) {
        return this.fieldMap.get(fieldName);
    }
}