package com.github.chaosfirebolt.mapper.dummy;

import com.github.chaosfirebolt.mapper.configuration.annotation.Access;
import com.github.chaosfirebolt.mapper.configuration.annotation.Collection;
import com.github.chaosfirebolt.mapper.configuration.annotation.Mapped;

import java.util.List;

/**
 * Created by ChaosFire on 29-Aug-18
 */
@Mapped
public class Teacher extends Person {

    @Access(getterName = "getStudents", setter = false)
    @Collection(elementType = Student.class)
    private List<Student> students;

    public Teacher(String name, int age, String job, List<Student> students) {
        super(name, age, job);
        this.students = students;
    }

    public List<Student> getStudents() {
        return this.students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}