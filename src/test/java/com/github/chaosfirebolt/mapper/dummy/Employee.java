package com.github.chaosfirebolt.mapper.dummy;

import com.github.chaosfirebolt.mapper.configuration.annotation.Access;
import com.github.chaosfirebolt.mapper.configuration.annotation.Mapped;

/**
 * Created by ChaosFire on 14-Sep-18
 */
@Mapped
public class Employee extends Person {

    @Access(setter = false)
    private Company company;
    @Access(setter = false)
    private String position;
    @Access(setter = false)
    private Double salary;
    @Access(setter = false)
    private Address address;
    @Access(setter = false)
    private Employee colleague;

    public Employee(String name, int age, String job, Company company, String position, Double salary, Address address) {
        super(name, age, job);
        this.company = company;
        this.position = position;
        this.salary = salary;
        this.address = address;
    }

    public Company getCompany() {
        return this.company;
    }

    public void setCompany(Company company) {
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

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Employee getColleague() {
        return this.colleague;
    }

    public void setColleague(Employee colleague) {
        this.colleague = colleague;
    }
}