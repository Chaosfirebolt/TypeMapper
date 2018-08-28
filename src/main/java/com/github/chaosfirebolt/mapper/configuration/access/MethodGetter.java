package com.github.chaosfirebolt.mapper.configuration.access;

import com.github.chaosfirebolt.mapper.exc.MappingException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by ChaosFire on 14-May-18
 */
class MethodGetter extends MethodAccessor implements Getter {

    MethodGetter(Method method) {
        super(method);
    }

    @Override
    public Object get(Object target) {
        try {
            return super.getMethod().invoke(target);
        } catch (IllegalAccessException | InvocationTargetException exc) {
            //IllegalAccessException should never be thrown
            throw new MappingException(exc.getMessage(), exc);
        }
    }
}