package com.github.chaosfirebolt.mapper.configuration.access;

import com.github.chaosfirebolt.mapper.configuration.annotation.Access;
import com.github.chaosfirebolt.mapper.exc.MappingException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by ChaosFire on 14-May-18
 */
public class AccessorFactory {

    public static Accessor createAccessor(Field field) {
        Access access = field.getAnnotation(Access.class);
        if (access == null) {
            return null;
        }
        Getter getter = null;
        if (access.getter()) {
            getter = access.getterName().equals("") ? new FieldGetter(field) : new MethodGetter(getMethod(field.getDeclaringClass(), access.getterName()));
        }
        Setter setter = null;
        if (access.setter()) {
            Class<?>[] setterParams = access.setterParams();
            if (setterParams.length == 0) {
                setterParams = new Class<?>[]{field.getType()};
            }
            setter = access.setterName().equals("") ? new FieldSetter(field) : new MethodSetter(getMethod(field.getDeclaringClass(), access.setterName(), setterParams));
        }
        return getter == null && setter == null ? null : new AccessorImpl(getter, setter);
    }

    private static Method getMethod(Class<?> clazz, String name, Class<?>... params) {
        try {
            return clazz.getDeclaredMethod(name, params);
        } catch (NoSuchMethodException exc) {
            String message = String.format("Unable to find method %s with parameters %s in class %s.", name, Arrays.toString(params), clazz);
            throw new MappingException(message, exc);
        }
    }
}