package com.github.chaosfirebolt.mapper;

import com.github.chaosfirebolt.mapper.configuration.ConfigurationFactory;
import com.github.chaosfirebolt.mapper.configuration.Direction;
import com.github.chaosfirebolt.mapper.constant.Mapper;
import com.github.chaosfirebolt.mapper.dummy.*;
import com.github.chaosfirebolt.mapper.testUtils.TestUtils;
import org.junit.After;
import org.junit.Before;

/**
 * Created by ChaosFire on 17-Sep-18
 */
public class ManualTypeMapperTests extends AbstractTypeMapperTests {

    public ManualTypeMapperTests() {
        super(MapperFactory.getMapper(Mapper.MANUAL));
    }

    @Before
    public void setup() {
        Direction<Entity, Dto> parentDirection = new Direction<>(Entity.class, Dto.class);
        ConfigurationFactory.getConfiguration(Mapper.MANUAL).mapping(parentDirection).composer()
                .transform(String.class, String.class).supplier(en -> en.getFirstName() + " " + en.getLastName()).consumer(Dto::setFullName)
                .compose()
                .transform(Integer.class, String.class).supplier(Entity::getAge).consumer(Dto::setAge).function(Object::toString)
                .finish().mapping(ExtEntity.class, ExtDto.class, ConfigurationFactory.getConfiguration(Mapper.MANUAL).mapping(parentDirection)).composer()
                .transform(String.class, String.class).supplier(ExtEntity::getAddress).consumer(ExtDto::setAddress)
                .finish().mapping(Person.class, PersonView.class).composer()
                .transform(String.class, String.class).supplier(Person::getName).consumer(PersonView::setName)
                .compose()
                .transform(String.class, String.class).supplier(Person::getJob).consumer(PersonView::setWork)
                .finish().mapping(Adult.class, AdultView.class).composer()
                .transform(Adult.class, AdultView.class).supplier(Adult::getFriend).consumer(AdultView::setFriend)
                    .function(adult -> adult == null ? null : super.getTypeMapper().map(adult, AdultView.class))
                .finish();
    }

    @After
    public void cleanup() {
        TestUtils.getConfigurationInstances().clear();
        TestUtils.getMapperInstances().clear();
    }
}