package com.github.chaosfirebolt.mapper;

import com.github.chaosfirebolt.mapper.configuration.access.Accessor;
import com.github.chaosfirebolt.mapper.configuration.access.Getter;
import com.github.chaosfirebolt.mapper.configuration.annotation.Access;
import com.github.chaosfirebolt.mapper.configuration.annotation.Mapped;
import com.github.chaosfirebolt.mapper.configuration.data.ClassContainer;
import com.github.chaosfirebolt.mapper.configuration.data.FieldContainer;
import com.github.chaosfirebolt.mapper.configuration.factory.ObjectFactory;
import com.github.chaosfirebolt.mapper.configuration.factory.ObjectFactoryImpl;
import com.github.chaosfirebolt.mapper.exc.MappingException;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Super class for all implementations of TypeMapper.
 *
 * Created by ChaosFire on 16-Apr-18
 */
abstract class AbstractMapper implements TypeMapper {

    private final ObjectFactory objectFactory;
    private final Map<Integer, Object> destinationRefs;

    AbstractMapper() {
        this.objectFactory = new ObjectFactoryImpl();
        this.destinationRefs = new ConcurrentHashMap<>();
    }

    <O> O createObject(Class<O> clazz) {
        return this.objectFactory.create(clazz);
    }

    void registerRef(int sourceHash, Object destObj) {
        if (!this.destinationRefs.containsKey(sourceHash)) {
            this.destinationRefs.put(sourceHash, destObj);
        }
    }

    Object getSeenRef(int sourceHash) {
        return this.destinationRefs.get(sourceHash);
    }

    void clearRef(int sourceHash) {
        this.destinationRefs.remove(sourceHash);
    }

    /**
     * Convenience method, returning the class of an object.
     *
     * @param object Object, whose class is to be returned.
     * @param <T> Type of the class.
     * @return The {@code Class} object of this object.
     */
    static <T> Class<T> genericClass(T object) {
        @SuppressWarnings("unchecked")
        Class<T> clazz = (Class<T>) object.getClass();
        return clazz;
    }

    @SuppressWarnings("unchecked")
    <S, D> void convert(S sourceObject, Class<S> sourceClass, D destinationObject, Class<D> destinationClass, boolean skipNonNull) {
        FieldContainer destinationContainer = ClassContainer.getInstance().getAccess(destinationClass);
        FieldContainer sourceContainer = ClassContainer.getInstance().getAccess(sourceClass);
        for (Map.Entry<String, Accessor> entry : destinationContainer.accessorMap().entrySet()) {
            String destFieldName = entry.getKey();
            if (skipNonNull) {
                Getter getter = entry.getValue().getter();
                if (getter != null && getter.get(destinationObject) != null) {
                    continue;
                }
                Field destField = destinationContainer.field(destFieldName);
                destField.setAccessible(true);
                try {
                    if (destField.get(destinationObject) != null) {
                        continue;
                    }
                } catch (IllegalAccessException ignored) {
                }
            }
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
        Object seenObj = this.getSeenRef(System.identityHashCode(object));
        if (seenObj != null) {
            return seenObj;
        }
        return mapped ? this.map(object, destType) : object;
    }
}