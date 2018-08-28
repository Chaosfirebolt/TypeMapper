package com.github.chaosfirebolt.mapper.dummy;

import com.github.chaosfirebolt.mapper.configuration.annotation.Access;
import com.github.chaosfirebolt.mapper.configuration.annotation.Mapped;

/**
 * Created by ChaosFire on 16-May-18
 */
@Mapped
public class Person {

    @Access(getterName = "getName", setter = false)
    private String name;
    private int age;
    @Access(setter = false)
    private String job;

    public Person(String name, int age, String job) {
        super();
        this.name = name;
        this.age = age;
        this.job = job;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getJob() {
        return this.job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}