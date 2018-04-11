package com.github.chaosfirebolt.mapper;

/**
 * Created by ChaosFire on 11.4.2018 г.
 */
public interface TypeMapper {

    <S, D> D map(S sourceObject, D destinationObject);
    <S, D> D map(S sourceObject, Class<D> destinationClass);
}