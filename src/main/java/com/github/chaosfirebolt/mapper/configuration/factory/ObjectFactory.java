package com.github.chaosfirebolt.mapper.configuration.factory;

/**
 * Interface used to create empty instances of mapped classes.
 *
 * Created by ChaosFire on 13-Apr-18
 */
public interface ObjectFactory {

    /**
     * Creates empty instance of object with type <O> from supplied class object.
     *
     * @param clazz Class of the object, to be created.
     * @param <O> Type of newly created object.
     * @return Instance of object with type <O>.
     * @throws com.github.chaosfirebolt.mapper.exc.MappingException if supplied class does not have a public, no-arg constructor,
     *          or if any other exception is thrown during object initialization.
     */
    <O> O create(Class<O> clazz);
}