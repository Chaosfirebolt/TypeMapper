package com.github.chaosfirebolt.mapper;

import com.github.chaosfirebolt.mapper.dummy.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by ChaosFire on 17-Sep-18
 */
abstract class AbstractTypeMapperTests {

    private final TypeMapper typeMapper;

    AbstractTypeMapperTests(TypeMapper typeMapper) {
        this.typeMapper = typeMapper;
    }

    TypeMapper getTypeMapper() {
        return this.typeMapper;
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

    @Test
    public void allValuesExist_ShouldMapCorrectly() {
        Employee henry = createEmployee("United", 10, "Sofia", "Bull", 20, "Henry", 25, "salaryman", "manager", 999.99);
        Employee harry = createEmployee("Disease", 15, "Pleven", "Grip", 35, "Harry", 28, "collector", "worker", 856.20);
        henry.setColleague(harry);
        harry.setColleague(henry);

        EmployeeView actual = this.typeMapper.map(henry, EmployeeView.class);
        assertEqualData(actual, henry, 0);
        assertNotNull(actual.getName());
        assertNotNull(actual.getWork());
        assertNotNull(actual.getSalary());
        assertNotNull(actual.getPosition());
        assertNotNull(actual.getCompany().getName());
        assertNotNull(actual.getCompany().getEmployeeCount());
        assertNotNull(actual.getAddressView().getCity());
        assertNotNull(actual.getAddressView().getNumber());
        assertNotNull(actual.getAddressView().getStreet());
        assertNotNull(actual.getColleague());
    }

    @Test
    public void someValuesNull_ShouldMapCorrectly() {
        Employee henry = createEmployee("United", 10, "Sofia", "Bull", 20, "Henry", 25, "salaryman", "manager", 999.99);
        Employee harry = createEmployee("Disease", 15, "Pleven", "Grip", 35, "Harry", 28, "collector", null, 856.20);
        henry.setColleague(harry);
        harry.setColleague(henry);
        harry.setAddress(null);

        EmployeeView actual = this.typeMapper.map(harry, EmployeeView.class);
        assertEqualData(actual, harry, 0);
    }

    private static void assertEqualData(EmployeeView view, Employee employee, int depth) {
        if (depth > 1) {
            return;
        }
        assertEquals(view.getName(), employee.getName());
        assertEquals(view.getWork(), employee.getJob());
        assertEquals(view.getSalary(), employee.getSalary());
        assertEquals(view.getPosition(), employee.getPosition());
        if (view.getCompany() != null && employee.getCompany() != null) {
            assertEquals(view.getCompany().getName(), employee.getCompany().getName());
            assertEquals(view.getCompany().getEmployeeCount(), employee.getCompany().getEmployeeCount());
        } else if ((view.getCompany() != null && employee.getCompany() == null) || (view.getCompany() == null && employee.getCompany() != null)) {
            throw new AssertionError();
        }
        if (view.getAddressView() != null && employee.getAddress() != null) {
            assertEquals(view.getAddressView().getCity(), employee.getAddress().getCity());
            assertEquals(view.getAddressView().getNumber(), employee.getAddress().getNumber());
            assertEquals(view.getAddressView().getStreet(), employee.getAddress().getStreet());
        } else if ((view.getAddressView() != null && employee.getAddress() == null) || (view.getAddressView() == null && employee.getAddress() != null)) {
            throw new AssertionError(view.getAddressView() + " -> " + employee.getAddress());
        }
        assertEqualData(view.getColleague(), employee.getColleague(), depth + 1);
    }

    private static Employee createEmployee(String comName, Integer emplCount, String city, String street, Integer num, String empName, int age, String job, String pos, Double salary) {
        Company company = new Company(comName, emplCount);
        Address address = new Address(city, street, num);
        return new Employee(empName, age, job, company, pos, salary, address);
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
    public void test_circular_both_sides() {
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