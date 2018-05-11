package com.github.chaosfirebolt.mapper;

import com.github.chaosfirebolt.mapper.configuration.ConfigurationFactory;
import com.github.chaosfirebolt.mapper.configuration.Direction;
import com.github.chaosfirebolt.mapper.constant.Mapper;
import com.github.chaosfirebolt.mapper.dummy.Dto;
import com.github.chaosfirebolt.mapper.dummy.Entity;
import com.github.chaosfirebolt.mapper.dummy.ExtDto;
import com.github.chaosfirebolt.mapper.dummy.ExtEntity;
import com.github.chaosfirebolt.mapper.testUtils.TestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * Created by ChaosFire on 11.4.2018 г.
 */
public class ManualTypeMapperTests {

    private Mapper mapper;
    private TypeMapper typeMapper;

    public ManualTypeMapperTests() {
        this.mapper = Mapper.MANUAL;
        this.typeMapper = MapperFactory.getMapper(this.mapper);
    }

    @Before
    public void setup() {
        Direction<Entity, Dto> parentDirection = new Direction<>(Entity.class, Dto.class);
        ConfigurationFactory.getConfiguration(this.mapper).mapping(parentDirection).composer()
                .transform(String.class, String.class).supplier(en -> en.getFirstName() + " " + en.getLastName()).consumer(Dto::setFullName)
                .compose()
                .transform(Integer.class, String.class).supplier(Entity::getAge).consumer(Dto::setAge).function(Object::toString)
                .finish().mapping(ExtEntity.class, ExtDto.class, ConfigurationFactory.getConfiguration(this.mapper).mapping(parentDirection)).composer()
                .transform(String.class, String.class).supplier(ExtEntity::getAddress).consumer(ExtDto::setAddress)
                .finish();
    }

    @After
    public void cleanup() {
        TestUtils.getConfigurationInstances().clear();
        TestUtils.getMapperInstances().clear();
    }

    @Test
    public void mapParentClasses_DestinationClass_ShouldReturnCorrectlyFilledObject() {
        Entity entity = this.getEntity();
        Dto actual = this.typeMapper.map(entity, Dto.class);

        Dto expected = new Dto();
        this.transform(entity, expected);

        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void mapParentClasses_DestinationObject_ShouldReturnCorrectlyFilledObject() {
        Entity entity = this.getEntity();

        Dto actual = new Dto();
        this.typeMapper.map(entity, actual);
        Dto expected = new Dto();
        this.transform(entity, expected);

        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void mapParentClasses_DestinationObject_ShouldReturnProvidedObject() {
        Entity entity = this.getEntity();

        Dto expected = new Dto();
        Dto actual = this.typeMapper.map(entity, expected);

        assertSame(expected, actual);
    }

    @Test
    public void mapChildrenClasses_DestinationClass_ShouldReturnCorrectlyFilledObject() {
        ExtEntity entity = this.getExtendedEntity();

        ExtDto expected = new ExtDto();
        this.transformExtended(entity, expected);

        ExtDto actual = this.typeMapper.map(entity, ExtDto.class);
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void mapChildrenClasses_DestinationObject_ShouldReturnCorrectlyFilledObject() {
        ExtEntity entity = this.getExtendedEntity();

        ExtDto expected = new ExtDto();
        this.transformExtended(entity, expected);
        ExtDto actual = new ExtDto();
        this.typeMapper.map(entity, actual);

        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void mapChildrenClasses_DestinationObject_ShouldReturnProvidedObject() {
        ExtEntity entity = this.getExtendedEntity();

        ExtDto expected = new ExtDto();
        ExtDto actual = this.typeMapper.map(entity, expected);

        assertSame(expected, actual);
    }

    private Entity getEntity() {
        Entity entity = new Entity();
        entity.setFirstName("John");
        entity.setLastName("Doe");
        entity.setAge(20);
        return entity;
    }

    private ExtEntity getExtendedEntity() {
        ExtEntity entity = new ExtEntity();
        entity.setFirstName("John");
        entity.setLastName("Doe");
        entity.setAge(20);
        entity.setAddress("some place");
        return entity;
    }

    private void transform(Entity entity, Dto dto) {
        dto.setFullName(entity.getFirstName() + " " + entity.getLastName());
        dto.setAge(Integer.toString(entity.getAge()));
    }

    private void transformExtended(ExtEntity entity, ExtDto dto) {
        this.transform(entity, dto);
        dto.setAddress(entity.getAddress());
    }
}