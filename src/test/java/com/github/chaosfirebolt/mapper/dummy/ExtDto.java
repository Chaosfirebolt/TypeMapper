package com.github.chaosfirebolt.mapper.dummy;

/**
 * Created by ChaosFire on 11-May-18
 */
public class ExtDto extends Dto {

    private String address;

    @Override
    public String toString() {
        return String.format("%s; %s", super.toString(), this.address);
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}