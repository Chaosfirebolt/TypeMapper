package com.github.chaosfirebolt.mapper.configuration;

import com.github.chaosfirebolt.mapper.configuration.action.ActionFactory;
import com.github.chaosfirebolt.mapper.constant.Mapper;
import com.github.chaosfirebolt.mapper.dummy.Dto;
import com.github.chaosfirebolt.mapper.dummy.Entity;
import com.github.chaosfirebolt.mapper.testUtils.TestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

/**
 * Created by ChaosFire on 11-May-18
 */
public class MappingTests {

    private Mapping<Entity, Dto> mapping;
    private Direction<Entity, Dto> direction;

    public MappingTests() {
        super();
        this.direction = new Direction<>(Entity.class, Dto.class);
    }

    @Before
    public void setup() {
        this.mapping = ConfigurationFactory.getConfiguration(Mapper.MANUAL).mapping(this.direction);
    }

    @After
    public void cleanup() {
        TestUtils.getConfigurationInstances().clear();
    }

    @Test
    public void afterCreation_ActionListShouldExist() {
        assertNotNull(this.mapping.getActions());
    }

    @Test
    public void registerAction_ShouldAddActionToList() {
        this.mapping.register(ActionFactory.createAction(Entity::getAge, Dto::setAge, Object::toString, Integer.class, String.class));
        int expectedSize = 1;
        assertEquals(expectedSize, this.mapping.getActions().size());
    }

    @Test
    public void composerMethod_EachCallShouldReturnNewInstance() {
        Composer<Entity, Dto, ?, ?> first = this.mapping.composer();
        Composer<Entity, Dto, ?, ?> second = this.mapping.composer();
        assertNotSame(first, second);
    }
}