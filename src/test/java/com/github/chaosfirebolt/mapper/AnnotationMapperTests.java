package com.github.chaosfirebolt.mapper;

import com.github.chaosfirebolt.mapper.constant.Mapper;
import com.github.chaosfirebolt.mapper.dummy.*;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by ChaosFire on 16-May-18
 */
public class AnnotationMapperTests {

    private TypeMapper typeMapper;

    public AnnotationMapperTests() {
        this.typeMapper = MapperFactory.getMapper(Mapper.ANNOTATION);
    }

    @Test
    public void test_simple() {
        Person person = new Person("Ivan", 20, "unemployed");
        PersonView view = this.typeMapper.map(person, PersonView.class);
        assertNotNull(view.getName());
        assertEquals(person.getName(), view.getName());
        assertNotNull(view.getWork());
        assertEquals(person.getJob(), view.getWork());
    }

    @Test
    public void test_extended() {
        Student student = new Student("George", 20, "student", UUID.randomUUID().toString());
        StudentView studentView = this.typeMapper.map(student, StudentView.class);
        assertNotNull(studentView.getName());
        assertEquals(student.getName(), studentView.getName());
        assertNotNull(studentView.getWork());
        assertEquals(student.getJob(), studentView.getWork());
        assertNotNull(studentView.getId());
        assertEquals(student.getId(), studentView.getId());
    }
}