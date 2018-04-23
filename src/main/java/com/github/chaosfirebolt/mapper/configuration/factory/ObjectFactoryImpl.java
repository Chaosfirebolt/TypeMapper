package com.github.chaosfirebolt.mapper.configuration.factory;

import com.github.chaosfirebolt.mapper.exc.MappingException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ChaosFire on 13-Apr-18
 */
public class ObjectFactoryImpl implements ObjectFactory {

    /**
     * Cached constructors of mapped classes.
     */
    private final Map<Class<?>, Constructor<?>> constructorMap;

    public ObjectFactoryImpl() {
        this.constructorMap = new HashMap<>();
    }

    @Override
    public <O> O create(Class<O> clazz) {
        @SuppressWarnings("unchecked")
        Constructor<O> constructor = (Constructor<O>) this.constructorMap.get(clazz);
        if (constructor == null) {
            try {
                constructor = clazz.getConstructor();
            } catch (NoSuchMethodException exc) {
                throw new MappingException("Mapped object must have public no argument constructor.", exc);
            }
            this.constructorMap.put(clazz, constructor);
        }
        O instance;
        try {
            instance = constructor.newInstance();
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException exc) {
            throw new MappingException("Unable to create instance of " + clazz.getName(), exc);
        }
        return instance;
    }
}