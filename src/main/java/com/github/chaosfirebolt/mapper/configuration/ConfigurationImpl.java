package com.github.chaosfirebolt.mapper.configuration;

import com.github.chaosfirebolt.mapper.constant.Mapper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by ChaosFire on 12-Apr-18
 */
class ConfigurationImpl implements Configuration {

    private final Map<Direction<?, ?>, Mapping<?, ?>> conversionMap;
    private final Map<Class<?>, Set<Class<?>>> directionMap;
    private final Mapper mapper;

    ConfigurationImpl(Mapper mapper) {
        this.conversionMap = new HashMap<>();
        this.directionMap = new HashMap<>();
        this.mapper = mapper;
    }

    @Override
    public <S, D> Mapping<S, D> mapping(Class<S> sourceClass, Class<D> destinationClass) {
        Direction<S, D> direction = new Direction<>(sourceClass, destinationClass);
        return this.getMapping(direction, null);
    }

    @Override
    public <S, D> Mapping<S, D> mapping(Direction<S, D> direction) {
        return this.getMapping(direction, null);
    }

    private <S, D> Mapping<S, D> getMapping(Direction<S, D> direction, Mapping<? super S, ? super D> parentMapping) {
        @SuppressWarnings("unchecked")
        Mapping<S, D> mapping = (Mapping<S, D>) this.conversionMap.get(direction);
        if (mapping == null) {
            if (parentMapping == null) {
                parentMapping = this.resolveParent(direction);
            }
            mapping = new MappingImpl<>(parentMapping, this.mapper);
            this.conversionMap.put(direction, mapping);
            Set<Class<?>> destinations = this.directionMap.computeIfAbsent(direction.getSourceClass(), key -> new HashSet<>());
            destinations.add(direction.getDestinationClass());
        }
        return mapping;
    }

    private <S, D> Mapping<? super S, ? super D> resolveParent(Direction<S, D> direction) {
        Class<? super S> source = direction.getSourceClass();
        Class<? super D> destination = direction.getDestinationClass().getSuperclass();
        Class<? super D> destStart = destination;
        while (!source.equals(Object.class)) {
            Set<Class<?>> destinations = this.directionMap.get(source);
            while (!destination.equals(Object.class)) {
                if (destinations != null && destinations.contains(destination)) {
                    Direction<? super S, ? super D> current = new Direction<>(source, destination);
                    @SuppressWarnings("unchecked")
                    Mapping<? super S, ? super D> mapping = (Mapping<? super S, ? super D>) this.conversionMap.get(current);
                    return mapping;
                }
                destination = destination.getSuperclass();
            }
            destination = destStart;
            source = source.getSuperclass();
        }
        return null;
    }

    @Override
    public <S, D> Mapping<S, D> mapping(Class<S> sourceClass, Class<D> destinationClass, Mapping<? super S, ? super D> parentMapping) {
        Direction<S, D> direction = new Direction<>(sourceClass, destinationClass);
        return this.getMapping(direction, parentMapping);
    }

    @Override
    public <S, D> Mapping<S, D> mapping(Direction<S, D> direction, Mapping<? super S, ? super D> parentMapping) {
        return this.getMapping(direction, parentMapping);
    }
}