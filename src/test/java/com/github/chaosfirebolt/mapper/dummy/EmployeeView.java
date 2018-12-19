package com.github.chaosfirebolt.mapper.dummy;

import com.github.chaosfirebolt.mapper.configuration.annotation.Access;
import com.github.chaosfirebolt.mapper.configuration.annotation.Mapped;

/**
 * Created by ChaosFire on 14-Sep-18
 */
@Mapped
public class EmployeeView extends PersonView {

    @Access(getter = false)
    private CompanyView company;
    @Access(getter = false)
    private String position;
    @Access(getter = false)
    private Double salary;
    private AddressView addressView;
    @Access(getter = false)
    private EmployeeView colleague;

    public CompanyView getCompany() {
        return this.company;
    }

    public void setCompany(CompanyView company) {
        this.company = company;
    }

    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Double getSalary() {
        return this.salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public AddressView getAddressView() {
        return this.addressView;
    }

    public void setAddressView(AddressView addressView) {
        this.addressView = addressView;
    }

    public EmployeeView getColleague() {
        return this.colleague;
    }

    public void setColleague(EmployeeView colleague) {
        this.colleague = colleague;
    }
}