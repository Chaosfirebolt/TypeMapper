package com.github.chaosfirebolt.mapper.configuration;

import com.github.chaosfirebolt.mapper.constant.Mapper;
import com.github.chaosfirebolt.mapper.dummy.Dto;
import com.github.chaosfirebolt.mapper.dummy.Entity;
import com.github.chaosfirebolt.mapper.exc.MappingException;
import com.github.chaosfirebolt.mapper.testUtils.TestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

/**
 * Created by ChaosFire on 11-May-18
 */
public class ComposerTests {

    private Direction<Entity, Dto> direction;
    private Composer<Entity, Dto, Integer, String> composer;

    public ComposerTests() {
        this.direction = new Direction<>(Entity.class, Dto.class);
    }

    @Before
    public void setup() {
        this.composer = ConfigurationFactory.getConfiguration(Mapper.MANUAL).mapping(this.direction).composer().transform(Integer.class, String.class);
    }

    @After
    public void cleanup() {
        TestUtils.getConfigurationInstances().clear();
    }

    @Test
    public void supplierShouldReturnSameComposerInstance() {
        assertSame(this.composer, this.composer.supplier(Entity::getAge));
    }

    @Test
    public void consumerShouldReturnSameComposerInstance() {
        assertSame(this.composer, this.composer.consumer(Dto::setAge));
    }

    @Test
    public void functionShouldReturnSameComposerInstance() {
        assertSame(this.composer, this.composer.function(Object::toString));
    }

    @Test(expected = MappingException.class)
    public void invalidComposition_ComposeShouldThrowException_Test1() {
        this.composer.function(Object::toString).compose();
    }

    @Test(expected = MappingException.class)
    public void invalidComposition_ComposeShouldThrowException_Test2() {
        this.composer.supplier(Entity::getAge).consumer(Dto::setAge).compose();
    }

    @Test
    public void validComposition_ComposeShouldRegisterNewAction() {
        this.composer.supplier(Entity::getAge).consumer(Dto::setAge).function(Object::toString).compose();
        int expectedSize = 1;
        assertEquals(expectedSize, ConfigurationFactory.getConfiguration(Mapper.MANUAL).mapping(this.direction).getActions().size());
    }

    @Test
    public void validComposition_ComposeShouldReturnNewInstanceOfComposer() {
        this.composer.supplier(Entity::getAge).consumer(Dto::setAge).function(Object::toString);
        assertNotSame(this.composer, this.composer.compose());
    }

    @Test(expected = MappingException.class)
    public void invalidComposition_FinishShouldThrowException_Test1() {
        this.composer.function(Object::toString).finish();
    }

    @Test(expected = MappingException.class)
    public void invalidComposition_FinishShouldThrowException_Test2() {
        this.composer.supplier(Entity::getAge).consumer(Dto::setAge).finish();
    }

    @Test
    public void validComposition_FinishShouldRegisterNewAction() {
        this.composer.supplier(Entity::getAge).consumer(Dto::setAge).function(Object::toString).finish();
        int expectedSize = 1;
        assertEquals(expectedSize, ConfigurationFactory.getConfiguration(Mapper.MANUAL).mapping(this.direction).getActions().size());
    }

    @Test
    public void validComposition_FinishShouldReturnSameInstanceOfConfiguration() {
        this.composer.supplier(Entity::getAge).consumer(Dto::setAge).function(Object::toString);
        assertSame(ConfigurationFactory.getConfiguration(Mapper.MANUAL), this.composer.finish());
    }
}