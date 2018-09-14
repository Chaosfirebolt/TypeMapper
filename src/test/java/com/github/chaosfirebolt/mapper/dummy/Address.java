package com.github.chaosfirebolt.mapper.dummy;

/**
 * Created by ChaosFire on 14-Sep-18
 */
public class Address {

    private String city;
    private String street;
    private Integer number;

    public Address() {
    }

    public Address(String city, String street, Integer number) {
        this.city = city;
        this.street = street;
        this.number = number;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumber() {
        return this.number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}