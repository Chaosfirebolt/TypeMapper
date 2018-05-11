package com.github.chaosfirebolt.mapper.configuration;

import com.github.chaosfirebolt.mapper.constant.Mapper;
import com.github.chaosfirebolt.mapper.dummy.Dto;
import com.github.chaosfirebolt.mapper.dummy.Entity;
import com.github.chaosfirebolt.mapper.dummy.ExtDto;
import com.github.chaosfirebolt.mapper.dummy.ExtEntity;
import com.github.chaosfirebolt.mapper.testUtils.TestUtils;
import org.junit.After;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

/**
 * Created by ChaosFire on 11-May-18
 */
public class ConfigurationTests {

    private Configuration configuration;
    private Map<Mapper, Configuration> instances;
    private Direction<Entity, Dto> direction;

    public ConfigurationTests() {
        super();
        this.configuration = ConfigurationFactory.getConfiguration(Mapper.MANUAL);
        this.instances = TestUtils.getConfigurationInstances();
        this.direction = new Direction<>(Entity.class, Dto.class);
    }

    @After
    public void cleanUp() {
        this.instances.clear();
    }

    @Test
    public void callToMappingShouldReturnInstanceOfMapping_Test1() {
        Mapping<Entity, Dto> mapping = this.configuration.mapping(new Direction<>(Entity.class, Dto.class));
        assertNotNull(mapping);
    }

    @Test
    public void callToMappingShouldReturnInstanceOfMapping_Test2() {
        Mapping<Entity, Dto> mapping = this.configuration.mapping(Entity.class, Dto.class);
        assertNotNull(mapping);
    }

    @Test
    public void callToMappingShouldReturnInstanceOfMapping_Test3() {
        Mapping<Entity, Dto> mapping = this.configuration.mapping(new Direction<>(Entity.class, Dto.class), null);
        assertNotNull(mapping);
    }

    @Test
    public void callToMappingShouldReturnInstanceOfMapping_Test4() {
        Mapping<Entity, Dto> mapping = this.configuration.mapping(Entity.class, Dto.class, null);
        assertNotNull(mapping);
    }

    @Test
    public void mappingExists_NextCallShouldReturnSameInstance_Test1() {
        Mapping<Entity, Dto> mapping = this.configuration.mapping(this.direction);
        assertSame(mapping, this.configuration.mapping(this.direction));
    }

    @Test
    public void mappingExists_NextCallShouldReturnSameInstance_Test2() {
        Mapping<Entity, Dto> mapping = this.configuration.mapping(this.direction);
        assertSame(mapping, this.configuration.mapping(Entity.class, Dto.class));
    }

    @Test
    public void mappingExists_NextCallShouldReturnSameInstance_Test3() {
        Mapping<Entity, Dto> mapping = this.configuration.mapping(Entity.class, Dto.class);
        assertSame(mapping, this.configuration.mapping(Entity.class, Dto.class));
    }

    @Test
    public void mappingExists_NextCallShouldReturnSameInstance_Test4() {
        Mapping<Entity, Dto> mapping = this.configuration.mapping(Entity.class, Dto.class);
        assertSame(mapping, this.configuration.mapping(this.direction));
    }

    @Test
    public void mappingExists_NextCallShouldReturnSameInstance_Test5() {
        Mapping<Entity, Dto> mapping = this.configuration.mapping(this.direction, null);
        assertSame(mapping, this.configuration.mapping(this.direction, null));
    }

    @Test
    public void mappingExists_NextCallShouldReturnSameInstance_Test6() {
        Mapping<Entity, Dto> mapping = this.configuration.mapping(this.direction, null);
        assertSame(mapping, this.configuration.mapping(Entity.class, Dto.class, null));
    }

    @Test
    public void mappingExists_NextCallShouldReturnSameInstance_Test7() {
        Mapping<Entity, Dto> mapping = this.configuration.mapping(Entity.class, Dto.class, null);
        assertSame(mapping, this.configuration.mapping(Entity.class, Dto.class, null));
    }

    @Test
    public void mappingExists_NextCallShouldReturnSameInstance_Test8() {
        Mapping<Entity, Dto> mapping = this.configuration.mapping(Entity.class, Dto.class, null);
        assertSame(mapping, this.configuration.mapping(this.direction, null));
    }

    @Test
    public void parentMappingExists_ShouldResolveCorrectly_Test1() {
        this.configuration.mapping(Entity.class, Dto.class);
        assertNotNull(this.configuration.mapping(ExtEntity.class, ExtDto.class).getParent());
    }

    @Test
    public void parentMappingExists_ShouldResolveCorrectly_Test2() {
        this.configuration.mapping(Entity.class, Dto.class);
        assertNotNull(this.configuration.mapping(Entity.class, ExtDto.class).getParent());
    }

    @Test
    public void parentMappingExists_ShouldResolveCorrectly_Test3() {
        this.configuration.mapping(Entity.class, Dto.class);
        assertNotNull(this.configuration.mapping(ExtEntity.class, ExtDto.class).getParent());
    }

    @Test
    public void noParentMappingBeforehand_ShouldSetProperly() {
        Direction<ExtEntity, ExtDto> direction = new Direction<>(ExtEntity.class, ExtDto.class);
        this.configuration.mapping(direction, this.configuration.mapping(Entity.class, Dto.class));
        assertNotNull(this.configuration.mapping(direction).getParent());
    }
}