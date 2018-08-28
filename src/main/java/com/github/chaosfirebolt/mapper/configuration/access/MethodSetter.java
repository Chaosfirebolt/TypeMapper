package com.github.chaosfirebolt.mapper.configuration.access;

import com.github.chaosfirebolt.mapper.exc.MappingException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by ChaosFire on 14-May-18
 */
class MethodSetter extends MethodAccessor implements Setter {

    MethodSetter(Method method) {
        super(method);
    }

    @Override
    public void set(Object target, Object value) {
        try {
            super.getMethod().invoke(target, value);
        } catch (IllegalAccessException | InvocationTargetException exc) {
            //IllegalAccessException should never happen
            throw new MappingException(exc.getMessage(), exc);
        }
    }
}