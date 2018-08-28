package com.github.chaosfirebolt.mapper.configuration.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ChaosFire on 15-May-18
 */
public final class ClassContainer {

    private static ClassContainer instance;

    private final Map<Class<?>, FieldContainer> accessMap;

    private ClassContainer() {
        this.accessMap = new HashMap<>();
    }

    public static ClassContainer getInstance() {
        if (instance == null) {
            instance = new ClassContainer();
        }
        return instance;
    }

    public FieldContainer getAccess(Class<?> clazz) {
        FieldContainer fieldContainer = this.accessMap.get(clazz);
        if (fieldContainer == null) {
            fieldContainer = FieldContainer.parse(clazz);
            this.accessMap.put(clazz, fieldContainer);
        }
        return fieldContainer;
    }
}