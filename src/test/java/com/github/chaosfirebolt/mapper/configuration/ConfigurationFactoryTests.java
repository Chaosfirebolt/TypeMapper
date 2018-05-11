package com.github.chaosfirebolt.mapper.configuration;

import com.github.chaosfirebolt.mapper.constant.Mapper;
import com.github.chaosfirebolt.mapper.testUtils.TestUtils;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ChaosFire on 10-May-18
 */
public class ConfigurationFactoryTests {

    private Mapper mapper;

    public ConfigurationFactoryTests() {
        super();
        this.mapper = Mapper.MANUAL;
    }

    @After
    public void cleanup() {
        TestUtils.getConfigurationInstances().clear();
    }

    @Test
    public void noConfigurationInstanceForThisMapper_NewInstanceShouldBeAddedToUnderlyingMap() {
        ConfigurationFactory.getConfiguration(this.mapper);
        assertNotNull(TestUtils.getConfigurationInstances().get(this.mapper));
    }

    @Test
    public void configurationInstanceForMapperExists_ShouldReturnSameInstance() {
        Configuration configuration = ConfigurationFactory.getConfiguration(this.mapper);
        assertSame(configuration, ConfigurationFactory.getConfiguration(this.mapper));
    }
}