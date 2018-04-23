package com.github.chaosfirebolt.mapper;

import com.github.chaosfirebolt.mapper.constant.Mapper;
import com.github.chaosfirebolt.mapper.exc.MappingException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Factory for {@link TypeMapper} instances.
 *
 * Created by ChaosFire on 16-Apr-18
 */
@SuppressWarnings("WeakerAccess")
public class MapperFactory {

    /**
     * Package where TypeMapper implementations are located.
     */
    private static final String PACKAGE = "com.github.chaosfirebolt.mapper.";

    /**
     * Cached instances of each mapper implementation.
     */
    private static Map<Mapper, TypeMapper> instances = new HashMap<>();

    /**
     * Returns instance of {@code TypeMapper} associated with provided {@link Mapper}
     * @param mapper Desired TypeMapper implementation.
     * @return Instance of TypeMapper.
     */
    @SuppressWarnings("unchecked")
    public static TypeMapper getMapper(Mapper mapper) {
        TypeMapper typeMapper = instances.get(mapper);
        if (typeMapper == null) {
            Class<TypeMapper> clazz;
            try {
                clazz = (Class<TypeMapper>) Class.forName(PACKAGE + mapper.getImplementation() + "Mapper");
            } catch (ClassNotFoundException exc) {
                throw new MappingException("TypeMapper implementation not found.", exc);
            }
            Constructor<TypeMapper> constructor;
            try {
                constructor = clazz.getDeclaredConstructor();
            } catch (NoSuchMethodException exc) {
                throw new MappingException("TypeMapper constructor not found.", exc);
            }
            constructor.setAccessible(true);
            try {
                typeMapper = constructor.newInstance();
            } catch (InstantiationException | InvocationTargetException | IllegalAccessException exc) {
                throw new MappingException("Unable to instantiate mapper.", exc);
            }
            instances.put(mapper, typeMapper);
        }
        return typeMapper;
    }
}