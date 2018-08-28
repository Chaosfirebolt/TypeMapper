package com.github.chaosfirebolt.mapper.dummy;

import com.github.chaosfirebolt.mapper.configuration.annotation.Access;
import com.github.chaosfirebolt.mapper.configuration.annotation.Mapped;

/**
 * Created by ChaosFire on 28-Aug-18
 */
@Mapped
public class Student extends Person {

    @Access(setter = false)
    private String id;

    public Student(String name, int age, String job, String id) {
        super(name, age, job);
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}