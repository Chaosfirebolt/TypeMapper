package com.github.chaosfirebolt.mapper;

import com.github.chaosfirebolt.mapper.configuration.ConfigurationFactory;
import com.github.chaosfirebolt.mapper.constant.Mapper;
import com.github.chaosfirebolt.mapper.dummy.*;
import com.github.chaosfirebolt.mapper.testUtils.TestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by ChaosFire on 14-Sep-18
 */
public class MixedMapperTests {

    private final TypeMapper typeMapper;

    public MixedMapperTests() {
        this.typeMapper = MapperFactory.getMapper(Mapper.MIXED);
    }

    @Before
    public void setup() {
        ConfigurationFactory.getConfiguration(Mapper.MIXED).mapping(Company.class, CompanyView.class)
                .composer().transform(Integer.class, Integer.class).supplier(Company::getEmployeeCount).consumer(CompanyView::setEmployeeCount)
                .finish().mapping(Address.class, AddressView.class)
                .composer().transform(String.class, String.class).supplier(Address::getCity).consumer(AddressView::setCity)
                .compose().transform(String.class, String.class).supplier(Address::getStreet).consumer(AddressView::setStreet)
                .compose().transform(Integer.class, Integer.class).supplier(Address::getNumber).consumer(AddressView::setNumber)
                .finish().mapping(Employee.class, EmployeeView.class)
                .composer().transform(String.class, String.class).supplier(Employee::getPosition).consumer(EmployeeView::setPosition)
                .compose().transform(Double.class, Double.class).supplier(Employee::getSalary).consumer(EmployeeView::setSalary)
                .compose().transform(Address.class, AddressView.class).supplier(Employee::getAddress).consumer(EmployeeView::setAddressView)
                    .function(address -> this.typeMapper.map(address, AddressView.class))
                .compose().transform(Employee.class, EmployeeView.class).supplier(Employee::getColleague).consumer(EmployeeView::setColleague)
                    .function(colleague -> this.typeMapper.map(colleague, EmployeeView.class))
                .finish();
    }

    @After
    public void cleanup() {
        TestUtils.getConfigurationInstances().clear();
        TestUtils.getMapperInstances().clear();
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
            throw new AssertionError();
        }
        assertEqualData(view.getColleague(), employee.getColleague(), depth + 1);
    }

    private static Employee createEmployee(String comName, Integer emplCount, String city, String street, Integer num, String empName, int age, String job, String pos, Double salary) {
        Company company = new Company(comName, emplCount);
        Address address = new Address(city, street, num);
        return new Employee(empName, age, job, company, pos, salary, address);
    }
}