package com.github.chaosfirebolt.mapper;

/**
 * Created by ChaosFire on 14-May-18
 */
@SuppressWarnings("unused")
public class AnnotationMapper extends AbstractMapper {

    AnnotationMapper() {
        super();
    }

    @Override
    public <S, D> D map(S sourceObject, D destinationObject) {
        super.registerRef(System.identityHashCode(sourceObject), destinationObject);
        super.convert(sourceObject, genericClass(sourceObject), destinationObject, genericClass(destinationObject), false);
        super.clearRefs();
        return destinationObject;
    }

    @Override
    public <S, D> D map(S sourceObject, Class<D> destinationClass) {
        D destinationObject = super.createObject(destinationClass);
        super.registerRef(System.identityHashCode(sourceObject), destinationObject);
        super.convert(sourceObject, genericClass(sourceObject), destinationObject, destinationClass, false);
        super.clearRefs();
        return destinationObject;
    }
}