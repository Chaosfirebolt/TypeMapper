package com.github.chaosfirebolt.mapper;

import com.github.chaosfirebolt.mapper.configuration.factory.ObjectFactory;
import com.github.chaosfirebolt.mapper.configuration.factory.ObjectFactoryImpl;

/**
 * Super class for all implementations of TypeMapper.
 *
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

    /**
     * Convenience method, returning the class of an object.
     *
     * @param object Object, whose class is to be returned.
     * @param <T> Type of the class.
     * @return The {@code Class} object of this object.
     */
    static <T> Class<T> genericClass(T object) {
        @SuppressWarnings("unchecked")
        Class<T> clazz = (Class<T>) object.getClass();
        return clazz;
    }
}