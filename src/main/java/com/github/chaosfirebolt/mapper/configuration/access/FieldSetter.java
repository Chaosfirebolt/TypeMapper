package com.github.chaosfirebolt.mapper.configuration.access;

import com.github.chaosfirebolt.mapper.exc.MappingException;

import java.lang.reflect.Field;

/**
 * Created by ChaosFire on 14-May-18
 */
class FieldSetter extends FieldAccessor implements Setter {

    FieldSetter(Field field) {
        super(field);
    }

    @Override
    public void set(Object target, Object value) {
        try {
            super.getField().set(target, value);
        } catch (IllegalAccessException exc) {
            //should never happen
            throw new MappingException(exc.getMessage(), exc);
        }
    }
}