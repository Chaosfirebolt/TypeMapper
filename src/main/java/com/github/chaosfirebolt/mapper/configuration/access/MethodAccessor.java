package com.github.chaosfirebolt.mapper.configuration.access;

import java.lang.reflect.Method;

/**
 * Created by ChaosFire on 14-May-18
 */
abstract class MethodAccessor {

    private final Method method;

    MethodAccessor(Method method) {
        method.setAccessible(true);
        this.method = method;
    }

    Method getMethod() {
        return this.method;
    }
}