package com.github.chaosfirebolt.mapper.configuration.access;

import com.github.chaosfirebolt.mapper.exc.MappingException;

import java.lang.reflect.Field;

/**
 * Created by ChaosFire on 14-May-18
 */
class FieldGetter extends FieldAccessor implements Getter {

    FieldGetter(Field field) {
        super(field);
    }

    @Override
    public Object get(Object target) {
        try {
            return super.getField().get(target);
        } catch (IllegalAccessException exc) {
            //should never happen
            throw new MappingException(exc.getMessage(), exc);
        }
    }
}