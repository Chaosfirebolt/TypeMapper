package com.github.chaosfirebolt.mapper.testUtils;

import com.github.chaosfirebolt.mapper.MapperFactory;
import com.github.chaosfirebolt.mapper.TypeMapper;
import com.github.chaosfirebolt.mapper.configuration.Configuration;
import com.github.chaosfirebolt.mapper.configuration.ConfigurationFactory;
import com.github.chaosfirebolt.mapper.constant.Mapper;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by ChaosFire on 11-May-18
 */
public class TestUtils {

    public static Map<Mapper, Configuration> getConfigurationInstances() {
        try {
            Field field = ConfigurationFactory.class.getDeclaredField("instances");
            field.setAccessible(true);
            @SuppressWarnings("unchecked")
            Map<Mapper, Configuration> instances = (Map<Mapper, Configuration>) field.get(null);
            return instances;
        } catch (NoSuchFieldException | IllegalAccessException exc) {
            throw new RuntimeException(exc);
            //should never happen
        }
    }

    public static Map<Mapper, TypeMapper> getMapperInstances() {
        try {
            Field field = MapperFactory.class.getDeclaredField("instances");
            field.setAccessible(true);
            @SuppressWarnings("unchecked")
            Map<Mapper, TypeMapper> instances = (Map<Mapper, TypeMapper>) field.get(null);
            return instances;
        } catch (NoSuchFieldException | IllegalAccessException exc) {
            throw new RuntimeException(exc);
            //should never happen
        }
    }
}