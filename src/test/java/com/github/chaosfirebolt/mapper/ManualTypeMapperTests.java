package com.github.chaosfirebolt.mapper;

import com.github.chaosfirebolt.mapper.configuration.ConfigurationFactory;
import com.github.chaosfirebolt.mapper.configuration.Direction;
import com.github.chaosfirebolt.mapper.constant.Mapper;
import com.github.chaosfirebolt.mapper.dummy.*;
import com.github.chaosfirebolt.mapper.testUtils.TestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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
                .finish().mapping(Person.class, PersonView.class).composer()
                .transform(String.class, String.class).supplier(Person::getName).consumer(PersonView::setName)
                .compose()
                .transform(String.class, String.class).supplier(Person::getJob).consumer(PersonView::setWork)
                .finish().mapping(Adult.class, AdultView.class).composer()
                .transform(Adult.class, AdultView.class).supplier(Adult::getFriend).consumer(AdultView::setFriend)
                    .function(adult -> adult == null ? null : this.typeMapper.map(adult, AdultView.class))
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

    @Test
    public void circularReference_OneSide_ShouldMapCorrect() {
        Adult george = new Adult("George", 31, "businessman");
        Adult jack = new Adult("Jack", 29, "salesman", george);

        AdultView expected = convertBasic(jack);
        expected.setFriend(convertBasic(george));
        AdultView actual = this.typeMapper.map(jack, AdultView.class);

        basicAssert(expected, actual);
        assertNotNull(actual.getFriend());
        basicAssert(expected.getFriend(), actual.getFriend());
        assertNull(actual.getFriend().getFriend());
    }

    @Test
    public void circularReference_BothSides_ShouldMapCorrect() {
        Adult george = new Adult("George", 31, "businessman");
        Adult jack = new Adult("Jack", 29, "salesman", george);
        george.setFriend(jack);

        AdultView expected = convertBasic(jack);
        AdultView georgeView = convertBasic(george);
        expected.setFriend(georgeView);
        georgeView.setFriend(expected);
        AdultView actual = this.typeMapper.map(jack, AdultView.class);

        basicAssert(expected, actual);
        assertNotNull(actual.getFriend());
        basicAssert(expected.getFriend(), actual.getFriend());
        assertNotNull(actual.getFriend().getFriend());
        assertSame(actual, actual.getFriend().getFriend());
        assertSame(actual.getFriend(), actual.getFriend().getFriend().getFriend());
    }

    @Test(expected = StackOverflowError.class)
    public void circularReference_BothSides_RecursionShouldThrowStackOverflow() {
        Adult george = new Adult("George", 31, "businessman");
        Adult jack = new Adult("Jack", 29, "salesman", george);
        george.setFriend(jack);

        AdultView expected = convertBasic(jack);
        AdultView georgeView = convertBasic(george);
        expected.setFriend(georgeView);
        georgeView.setFriend(expected);
        AdultView actual;
        try {
            actual = this.typeMapper.map(jack, AdultView.class);
        } catch (StackOverflowError exc) {
            throw new RuntimeException(exc);
        }
        recursiveGetFriend(actual);
    }

    private static void recursiveGetFriend(AdultView adultView) {
        if (adultView == null) {
            return;
        }
        recursiveGetFriend(adultView.getFriend());
    }

    private static void basicAssert(AdultView expected, AdultView actual) {
        assertNotNull(actual.getName());
        assertEquals(expected.getName(), actual.getName());
        assertNotNull(actual.getWork());
        assertEquals(expected.getWork(), actual.getWork());
    }

    private static AdultView convertBasic(Adult adult) {
        if (adult == null) {
            return null;
        }
        AdultView adultView = new AdultView();
        adultView.setName(adult.getName());
        adultView.setWork(adult.getJob());
        return adultView;
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