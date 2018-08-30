package com.github.chaosfirebolt.mapper;

import com.github.chaosfirebolt.mapper.configuration.access.Accessor;
import com.github.chaosfirebolt.mapper.configuration.annotation.Access;
import com.github.chaosfirebolt.mapper.configuration.annotation.Mapped;
import com.github.chaosfirebolt.mapper.configuration.data.FieldContainer;
import com.github.chaosfirebolt.mapper.configuration.data.ClassContainer;
import com.github.chaosfirebolt.mapper.exc.MappingException;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

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
        this.convert(sourceObject, genericClass(sourceObject), destinationObject, genericClass(destinationObject));
        super.clearRefs();
        return destinationObject;
    }

    @Override
    public <S, D> D map(S sourceObject, Class<D> destinationClass) {
        D destinationObject = super.createObject(destinationClass);
        super.registerRef(System.identityHashCode(sourceObject), destinationObject);
        this.convert(sourceObject, genericClass(sourceObject), destinationObject, destinationClass);
        super.clearRefs();
        return destinationObject;
    }

    @SuppressWarnings("unchecked")
    private <S, D> void convert(S sourceObject, Class<S> sourceClass, D destinationObject, Class<D> destinationClass) {
        FieldContainer destinationContainer = ClassContainer.getInstance().getAccess(destinationClass);
        FieldContainer sourceContainer = ClassContainer.getInstance().getAccess(sourceClass);
        for (Map.Entry<String, Accessor> entry : destinationContainer.accessorMap().entrySet()) {
            String destFieldName = entry.getKey();
            String sourceFieldName = resolveSourceFieldName(destinationContainer.field(destFieldName));
            if (destinationContainer.isCollection(destFieldName)) {
                Class<?> destElemType = collectionElemType(destinationContainer, destFieldName);
                Class<?> sourceElemType = collectionElemType(sourceContainer, sourceFieldName);
                Collection sourceCollection = (Collection) sourceContainer.accessorMap().get(sourceFieldName).getter().get(sourceObject);
                Collection destinationCollection = (Collection) destinationContainer.accessorMap().get(destFieldName).getter().get(destinationObject);
                if (destinationCollection == null) {
                    throw new MappingException("Collections should be instantiated in constructor - " + destFieldName + " in " + destinationClass.getName());
                }
                boolean mapped = areMapped(sourceElemType, destElemType);
                for (Object object : sourceCollection) {
                    destinationCollection.add(this.newElement(object, destElemType, mapped));
                }
                entry.getValue().setter().set(destinationObject, destinationCollection);
            } else {
                Class<?> destElemType = destinationContainer.field(destFieldName).getType();
                Class<?> sourceElemType = sourceContainer.field(sourceFieldName).getType();
                Object sourceFieldValue = sourceContainer.accessorMap().get(sourceFieldName).getter().get(sourceObject);
                Object result = this.newElement(sourceFieldValue, destElemType, areMapped(sourceElemType, destElemType));
                entry.getValue().setter().set(destinationObject, result);
            }
        }
    }

    private static String resolveSourceFieldName(Field destinationField) {
        String declaredName = destinationField.getAnnotation(Access.class).from();
        return declaredName.equals("") ? destinationField.getName() : declaredName;
    }

    private static Class<?> collectionElemType(FieldContainer fieldContainer, String fieldName) {
        return fieldContainer.field(fieldName).getAnnotation(com.github.chaosfirebolt.mapper.configuration.annotation.Collection.class).elementType();
    }

    private static boolean areMapped(Class<?> sourceType, Class<?> destType) {
        return sourceType.getAnnotation(Mapped.class) != null && destType.getAnnotation(Mapped.class) != null;
    }

    private Object newElement(Object object, Class<?> destType, boolean mapped) {
        if (object == null) {
            return null;
        }
        Object seenObj = super.getSeenRef(System.identityHashCode(object));
        if (seenObj != null) {
            return seenObj;
        }
        return mapped ? this.map(object, destType) : object;
    }
}