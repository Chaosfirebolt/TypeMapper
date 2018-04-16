package com.github.chaosfirebolt.mapper.configuration.action;

/**
 * Created by ChaosFire on 11-Apr-18
 */
public interface Action<S, D> {

    void perform(S sourceObject, D destinationObject);
}