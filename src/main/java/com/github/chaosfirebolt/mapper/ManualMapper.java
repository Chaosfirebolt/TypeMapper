package com.github.chaosfirebolt.mapper;

import com.github.chaosfirebolt.mapper.configuration.Configuration;
import com.github.chaosfirebolt.mapper.configuration.Direction;
import com.github.chaosfirebolt.mapper.configuration.action.Action;

import java.util.List;

/**
 * Created by ChaosFire on 13-Apr-18
 */
@SuppressWarnings("unused")
class ManualMapper extends AbstractMapper {

    private final Configuration configuration;

    ManualMapper() {
        super();
        this.configuration = Configuration.getConfiguration();
    }

    @Override
    public <S, D> D map(S sourceObject, D destinationObject) {
        Direction<S, D> direction = new Direction<>(genericClass(sourceObject), genericClass(destinationObject));
        List<Action<S, D>> actions = this.configuration.mapping(direction).getActions();
        for (Action<S, D> action : actions) {
            action.perform(sourceObject, destinationObject);
        }
        return destinationObject;
    }

    @Override
    public <S, D> D map(S sourceObject, Class<D> destinationClass) {
        D destinationObject = super.getObjectFactory().create(destinationClass);
        return this.map(sourceObject, destinationObject);
    }
}