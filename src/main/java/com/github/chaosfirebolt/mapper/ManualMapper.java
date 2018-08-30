package com.github.chaosfirebolt.mapper;

import com.github.chaosfirebolt.mapper.configuration.Configuration;
import com.github.chaosfirebolt.mapper.configuration.ConfigurationFactory;
import com.github.chaosfirebolt.mapper.configuration.Direction;
import com.github.chaosfirebolt.mapper.configuration.Mapping;
import com.github.chaosfirebolt.mapper.configuration.action.Action;
import com.github.chaosfirebolt.mapper.constant.Mapper;

import java.util.List;

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
        Direction<S, D> direction = new Direction<>(genericClass(sourceObject), genericClass(destinationObject));
        this.performAll(sourceObject, destinationObject, this.configuration.mapping(direction));
        return destinationObject;
    }

    private <S, D> void performAll(S source, D destination, Mapping<S, D> mapping) {
        if (mapping == null) {
            return;
        }
        this.performAll(source, destination, mapping.getParent());
        List<Action<S, D>> actions = mapping.getActions();
        for (Action<S, D> action : actions) {
            action.perform(source, destination);
        }
    }

    @Override
    public <S, D> D map(S sourceObject, Class<D> destinationClass) {
        D destinationObject = super.createObject(destinationClass);
        return this.map(sourceObject, destinationObject);
    }
}