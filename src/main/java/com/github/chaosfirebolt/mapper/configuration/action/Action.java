package com.github.chaosfirebolt.mapper.configuration.action;

/**
 * Action performed on objects of types <S> and <D>.
 *
 * @param <S> Type of source object.
 * @param <D> Type of destination object.
 *
 * Created by ChaosFire on 11-Apr-18
 */
public interface Action<S, D> {

    /**
     * Extracts data from a source object and sets it in destination object.
     *
     * @param sourceObject Source object, target for data extraction.
     * @param destinationObject Destination object, target for setting data.
     */
    void perform(S sourceObject, D destinationObject);
}