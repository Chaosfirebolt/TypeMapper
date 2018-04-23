package com.github.chaosfirebolt.mapper;

/**
 * Main functionality is specified by this interface.
 * Declares transformation methods.
 *
 * Created by ChaosFire on 11.4.2018 г.
 */
public interface TypeMapper {

    /**
     * Extracts data from source object and puts it in destination object, according to the configuration.
     *
     * @param sourceObject Object being transformed into destination object.
     * @param destinationObject Object to be filled with data from source object.
     * @param <S> Type of the source object.
     * @param <D> Type of the destination object.
     * @return Supplied destination object, with data taken from source object.
     */
    <S, D> D map(S sourceObject, D destinationObject);
    /**
     * Extracts data from the source object, creates object from destination class and fills it with data from source object, according to the configuration.
     *
     * @param sourceObject Object being transformed into destination object.
     * @param destinationClass Class of the destination object
     * @param <S> Type of the source object.
     * @param <D> Type of the destination object.
     * @return New object of type D, with data taken from source object.
     */
    <S, D> D map(S sourceObject, Class<D> destinationClass);
}