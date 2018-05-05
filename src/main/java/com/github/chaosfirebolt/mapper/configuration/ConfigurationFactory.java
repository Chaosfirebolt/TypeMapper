package com.github.chaosfirebolt.mapper.configuration;

import com.github.chaosfirebolt.mapper.constant.Mapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory for {@link Configuration} instances.
 *
 * Created by ChaosFire on 5.5.2018 г.
 */
public class ConfigurationFactory {

    /**
     * Instances of {@code Configuration} corresponding to each {@link Mapper}
     */
    private static Map<Mapper, Configuration> instances = new HashMap<>();

    /**
     * Gets existing instance of {@link Configuration} from the underlying map, or creates new one,
     * if there is no such instance, and returns it.
     *
     * @param mapper {@code Mapper} whose corresponding instance is needed.
     * @return Instance of {@link Configuration} associated with provided {@link Mapper}.
     */
    public static Configuration getConfiguration(Mapper mapper) {
        Configuration configuration = instances.get(mapper);
        if (configuration == null) {
            configuration = new ConfigurationImpl(mapper);
            instances.put(mapper, configuration);
        }
        return configuration;
    }
}