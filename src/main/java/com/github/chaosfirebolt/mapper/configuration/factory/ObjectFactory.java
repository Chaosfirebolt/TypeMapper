package com.github.chaosfirebolt.mapper.configuration.factory;

/**
 * Created by ChaosFire on 13-Apr-18
 */
public interface ObjectFactory {

    <O> O create(Class<O> clazz);
}