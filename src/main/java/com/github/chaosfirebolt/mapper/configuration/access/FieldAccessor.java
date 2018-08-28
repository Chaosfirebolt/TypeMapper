package com.github.chaosfirebolt.mapper.configuration.access;

import java.lang.reflect.Field;

/**
 * Created by ChaosFire on 14-May-18
 */
abstract class FieldAccessor {

    private final Field field;

    FieldAccessor(Field field) {
        field.setAccessible(true);
        this.field = field;
    }

    Field getField() {
        return this.field;
    }
}