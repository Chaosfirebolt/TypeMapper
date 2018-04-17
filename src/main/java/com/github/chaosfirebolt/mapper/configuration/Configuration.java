package com.github.chaosfirebolt.mapper.configuration;

/**
 * Created by ChaosFire on 12-Apr-18
 */
public interface Configuration {

    static Configuration getConfiguration() {
        return ConfigurationImpl.getInstance();
    }
    <S, D> Mapping<S, D> mapping(Direction<S, D> direction);
    <S, D> Mapping<S, D> mapping(Class<S> sourceClass, Class<D> destinationClass);
    <S, D> Mapping<S, D> mapping(Direction<S, D> direction, Mapping<? super S, ? super D> parentMapping);
    <S, D> Mapping<S, D> mapping(Class<S> sourceClass, Class<D> destinationClass, Mapping<? super S, ? super D> parentMapping);
}