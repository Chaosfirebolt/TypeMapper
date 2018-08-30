package com.github.chaosfirebolt.mapper;

import com.github.chaosfirebolt.mapper.constant.Mapper;
import com.github.chaosfirebolt.mapper.dummy.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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

    @Test
    public void test_collection() {
        List<Student> students = new ArrayList<>();
        students.add(new Student("George", 20, "pizza", UUID.randomUUID().toString()));
        students.add(new Student("John", 25, "pasta", UUID.randomUUID().toString()));
        students.add(new Student("Bill", 19, "random", UUID.randomUUID().toString()));
        Teacher teacher = new Teacher("Jack", 40, "teacher", students);
        TeacherView actual = this.typeMapper.map(teacher, TeacherView.class);

        List<StudentView> expectedStudents = new ArrayList<>();
        for (Student student : students) {
            expectedStudents.add(this.typeMapper.map(student, StudentView.class));
        }
        TeacherView expected = new TeacherView();
        expected.setName(teacher.getName());
        expected.setWork(teacher.getJob());
        expected.setStudents(expectedStudents);

        assertNotNull(actual.getName());
        assertEquals(expected.getName(), actual.getName());
        assertNotNull(actual.getWork());
        assertEquals(expected.getWork(), actual.getWork());

        assertNotNull(actual.getStudents());
        Comparator<StudentView> comparator = Comparator.comparing(StudentView::getId);
        List<StudentView> expectedCollection = expected.getStudents();
        expectedCollection.sort(comparator);
        List<StudentView> actualCollection = actual.getStudents();
        actualCollection.sort(comparator);
        assertEquals(expected.getStudents().size(), actual.getStudents().size());
        for (int i = 0; i < expectedCollection.size(); i++) {
            assertEquals(expectedCollection.get(i).getName(), actualCollection.get(i).getName());
            assertEquals(expectedCollection.get(i).getWork(), actualCollection.get(i).getWork());
            assertEquals(expectedCollection.get(i).getId(), actualCollection.get(i).getId());
        }
    }

    @Test
    public void test_circular_one_side() {
        Adult george = new Adult("George", 31, "businessman");
        Adult jack = new Adult("Jack", 29, "salesman", george);

        AdultView actual = this.typeMapper.map(jack, AdultView.class);
        assertNotNull(actual.getFriend());
        assertNull(actual.getFriend().getFriend());
        assertEqualData(actual, jack);
        assertEqualData(actual.getFriend(), george);
    }

    @Test
    public void test_circular_Both_sides() {
        Adult george = new Adult("George", 31, "businessman");
        Adult jack = new Adult("Jack", 29, "salesman", george);
        george.setFriend(jack);

        AdultView actual = this.typeMapper.map(jack, AdultView.class);
        assertNotNull(actual.getFriend());
        assertNotNull(actual.getFriend().getFriend());
        assertEqualData(actual, jack);
        assertEqualData(actual.getFriend(), george);
    }

    private static void assertEqualData(AdultView actual, Adult source) {
        assertNotNull(actual.getName());
        assertEquals(source.getName(), actual.getName());
        assertNotNull(actual.getWork());
        assertEquals(source.getJob(), actual.getWork());
    }
}