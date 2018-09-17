package com.github.chaosfirebolt.mapper;

import com.github.chaosfirebolt.mapper.configuration.Configuration;
import com.github.chaosfirebolt.mapper.configuration.ConfigurationFactory;
import com.github.chaosfirebolt.mapper.configuration.Direction;
import com.github.chaosfirebolt.mapper.constant.Mapper;

import static com.github.chaosfirebolt.mapper.MapperUtil.performActions;

/**
 * Implementation of {@link TypeMapper}, requiring manual configuration.
 *
 * Created by ChaosFire on 13-Apr-18
 */
@SuppressWarnings("unused")
class ManualMapper extends AbstractMapper {

    private final Configuration configuration;

    ManualMapper() {
        super();
        this.configuration = ConfigurationFactory.getConfiguration(Mapper.MANUAL);
    }

    @Override
    public <S, D> D map(S sourceObject, D destinationObject) {
        Object seen = super.getSeenRef(System.identityHashCode(sourceObject));
        if (seen != null) {
            return genericClass(destinationObject).cast(seen);
        }
        super.registerRef(System.identityHashCode(sourceObject), destinationObject);
        Direction<S, D> direction = new Direction<>(genericClass(sourceObject), genericClass(destinationObject));
        performActions(sourceObject, destinationObject, this.configuration.mapping(direction));
        super.clearRef(System.identityHashCode(sourceObject));
        return destinationObject;
    }

    @Override
    public <S, D> D map(S sourceObject, Class<D> destinationClass) {
        Object seen = super.getSeenRef(System.identityHashCode(sourceObject));
        if (seen != null) {
            return destinationClass.cast(seen);
        }
        D destinationObject = super.createObject(destinationClass);
        super.registerRef(System.identityHashCode(sourceObject), destinationObject);
        performActions(sourceObject, destinationObject, this.configuration.mapping(genericClass(sourceObject), destinationClass));
        super.clearRef(System.identityHashCode(sourceObject));
        return destinationObject;
    }
}