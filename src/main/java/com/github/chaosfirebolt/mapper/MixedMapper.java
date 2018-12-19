package com.github.chaosfirebolt.mapper;

import com.github.chaosfirebolt.mapper.configuration.Configuration;
import com.github.chaosfirebolt.mapper.configuration.ConfigurationFactory;
import com.github.chaosfirebolt.mapper.constant.Mapper;

import static com.github.chaosfirebolt.mapper.MapperUtil.performActions;

/**
 * Created by ChaosFire on 13-Sep-18
 */
@SuppressWarnings("unused")
class MixedMapper extends AbstractMapper {

    private final Configuration configuration;

    MixedMapper() {
        super();
        this.configuration = ConfigurationFactory.getConfiguration(Mapper.MIXED);
    }

    @Override
    public <S, D> D map(S sourceObject, D destinationObject) {
        Object seen = super.getSeenRef(System.identityHashCode(sourceObject));
        if (seen != null) {
            return genericClass(destinationObject).cast(seen);
        }
        super.registerRef(System.identityHashCode(sourceObject), destinationObject);
        Class<S> sourceClass = genericClass(sourceObject);
        Class<D> destinationClass = genericClass(destinationObject);
        performActions(sourceObject, destinationObject, this.configuration.mapping(sourceClass, destinationClass));
        super.convert(sourceObject, sourceClass, destinationObject, destinationClass, true);
        super.clearRef(System.identityHashCode(sourceObject));
        return destinationObject;
    }

    @Override
    public <S, D> D map(S sourceObject, Class<D> destinationClass) {
        if (sourceObject == null) {
            return null;
        }
        Object seen = super.getSeenRef(System.identityHashCode(sourceObject));
        if (seen != null) {
            return destinationClass.cast(seen);
        }
        D destinationObject = super.createObject(destinationClass);
        super.registerRef(System.identityHashCode(sourceObject), destinationObject);
        Class<S> sourceClass = genericClass(sourceObject);
        super.convert(sourceObject, sourceClass, destinationObject, destinationClass, false);
        performActions(sourceObject, destinationObject, this.configuration.mapping(sourceClass, destinationClass));
        super.clearRef(System.identityHashCode(sourceObject));
        return destinationObject;
    }
}