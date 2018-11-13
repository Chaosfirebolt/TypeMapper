package com.github.chaosfirebolt.mapper;

import com.github.chaosfirebolt.mapper.configuration.ConfigurationFactory;
import com.github.chaosfirebolt.mapper.configuration.Direction;
import com.github.chaosfirebolt.mapper.constant.Mapper;
import com.github.chaosfirebolt.mapper.dummy.*;
import com.github.chaosfirebolt.mapper.testUtils.TestUtils;
import org.junit.After;
import org.junit.Before;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ChaosFire on 17-Sep-18
 */
public class ManualTypeMapperTests extends AbstractTypeMapperTests {

    @SuppressWarnings("unchecked")
    private static final Class<List<Student>> STUDENT_LIST_CLASS = (Class<List<Student>>) (Class<?>) List.class;
    @SuppressWarnings("unchecked")
    private static final Class<List<StudentView>> STUDENT_VIEW_LIST_CLASS = (Class<List<StudentView>>) (Class<?>) List.class;

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
                    .finish()
                .mapping(ExtEntity.class, ExtDto.class, ConfigurationFactory.getConfiguration(Mapper.MANUAL).mapping(parentDirection)).composer()
                .transform(String.class, String.class).supplier(ExtEntity::getAddress).consumer(ExtDto::setAddress)
                    .finish()
                .mapping(Person.class, PersonView.class).composer()
                .transform(String.class, String.class).supplier(Person::getName).consumer(PersonView::setName)
                .compose()
                .transform(String.class, String.class).supplier(Person::getJob).consumer(PersonView::setWork)
                    .finish()
                .mapping(Adult.class, AdultView.class).composer()
                .transform(Adult.class, AdultView.class).supplier(Adult::getFriend).consumer(AdultView::setFriend)
                .function(adult -> adult == null ? null : super.getTypeMapper().map(adult, AdultView.class))
                    .finish()
                .mapping(Student.class, StudentView.class).composer()
                .transform(String.class, String.class).supplier(Student::getId).consumer(StudentView::setId)
                    .finish()
                .mapping(Teacher.class, TeacherView.class).composer()
                .transform(STUDENT_LIST_CLASS, STUDENT_VIEW_LIST_CLASS).supplier(Teacher::getStudents).consumer(TeacherView::setStudents)
                .function(list -> list.stream().map(student -> super.getTypeMapper().map(student, StudentView.class)).collect(Collectors.toList()))
                    .finish();
    }

    @After
    public void cleanup() {
        TestUtils.getConfigurationInstances().clear();
        TestUtils.getMapperInstances().clear();
    }
}