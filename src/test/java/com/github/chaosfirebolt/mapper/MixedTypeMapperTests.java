package com.github.chaosfirebolt.mapper;

import com.github.chaosfirebolt.mapper.configuration.ConfigurationFactory;
import com.github.chaosfirebolt.mapper.constant.Mapper;
import com.github.chaosfirebolt.mapper.dummy.*;
import com.github.chaosfirebolt.mapper.testUtils.TestUtils;
import org.junit.After;
import org.junit.Before;

/**
 * Created by ChaosFire on 17-Sep-18
 */
public class MixedTypeMapperTests extends AbstractTypeMapperTests {

    public MixedTypeMapperTests() {
        super(MapperFactory.getMapper(Mapper.MIXED));
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
                    .function(address -> super.getTypeMapper().map(address, AddressView.class))
                .compose().transform(Employee.class, EmployeeView.class).supplier(Employee::getColleague).consumer(EmployeeView::setColleague)
                    .function(colleague -> super.getTypeMapper().map(colleague, EmployeeView.class))
                .finish();
    }

    @After
    public void cleanup() {
        TestUtils.getConfigurationInstances().clear();
        TestUtils.getMapperInstances().clear();
    }
}