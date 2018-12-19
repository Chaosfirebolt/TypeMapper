package com.github.chaosfirebolt.mapper.dummy;

import com.github.chaosfirebolt.mapper.configuration.annotation.Access;
import com.github.chaosfirebolt.mapper.configuration.annotation.Mapped;

/**
 * Created by ChaosFire on 14-Sep-18
 */
@Mapped
public class AddressView {

    @Access(getter = false)
    private String city;
    @Access(getter = false)
    private String street;
    @Access(getter = false)
    private Integer number;

    public AddressView() {
    }

    public AddressView(String city, String street, Integer number) {
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