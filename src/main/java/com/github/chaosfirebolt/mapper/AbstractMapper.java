package com.github.chaosfirebolt.mapper;

import com.github.chaosfirebolt.mapper.configuration.factory.ObjectFactory;
import com.github.chaosfirebolt.mapper.configuration.factory.ObjectFactoryImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Super class for all implementations of TypeMapper.
 *
 * Created by ChaosFire on 16-Apr-18
 */
abstract class AbstractMapper implements TypeMapper {

    private final ObjectFactory objectFactory;
    private final Map<Integer, Object> destinationRefs;

    AbstractMapper() {
        this.objectFactory = new ObjectFactoryImpl();
        this.destinationRefs = new ConcurrentHashMap<>();
    }

    <O> O createObject(Class<O> clazz) {
        return this.objectFactory.create(clazz);
    }

    void registerRef(int sourceHash, Object destObj) {
        if (!this.destinationRefs.containsKey(sourceHash)) {
            this.destinationRefs.put(sourceHash, destObj);
        }
    }

    Object getSeenRef(int sourceHash) {
        return this.destinationRefs.get(sourceHash);
    }

    void clearRefs() {
        this.destinationRefs.clear();
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