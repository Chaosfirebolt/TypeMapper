package com.github.chaosfirebolt.mapper.configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ChaosFire on 12-Apr-18
 */
class ConfigurationImpl implements Configuration {

    private static Configuration instance;

    private final Map<Direction<?, ?>, Mapping<?, ?>> conversionMap;

    private ConfigurationImpl() {
        this.conversionMap = new HashMap<>();
    }

    static Configuration getInstance() {
        if (instance == null) {
            instance = new ConfigurationImpl();
        }
        return instance;
    }

    @Override
    public <S, D> Mapping<S, D> mapping(Class<S> sourceClass, Class<D> destinationClass) {
        Direction<S, D> direction = new Direction<>(sourceClass, destinationClass);
        return this.getMapping(direction);
    }

    @Override
    public <S, D> Mapping<S, D> mapping(Direction<S, D> direction) {
        return this.getMapping(direction);
    }

    private <S, D> Mapping<S, D> getMapping(Direction<S, D> direction) {
        @SuppressWarnings("unchecked")
        Mapping<S, D> mapping = (Mapping<S, D>) this.conversionMap.get(direction);
        if (mapping == null) {
            mapping = new MappingImpl<>();
            this.conversionMap.put(direction, mapping);
        }
        return mapping;
    }
}