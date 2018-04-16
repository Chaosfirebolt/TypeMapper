package com.github.chaosfirebolt.mapper;

import com.github.chaosfirebolt.mapper.configuration.factory.ObjectFactory;
import com.github.chaosfirebolt.mapper.configuration.factory.ObjectFactoryImpl;

/**
 * Created by ChaosFire on 16-Apr-18
 */
abstract class AbstractMapper implements TypeMapper {

    private final ObjectFactory objectFactory;

    AbstractMapper() {
        this.objectFactory = new ObjectFactoryImpl();
    }

    ObjectFactory getObjectFactory() {
        return this.objectFactory;
    }

    static <T> Class<T> genericClass(T object) {
        @SuppressWarnings("unchecked")
        Class<T> clazz = (Class<T>) object.getClass();
        return clazz;
    }
}