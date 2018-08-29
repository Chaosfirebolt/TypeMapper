package com.github.chaosfirebolt.mapper.dummy;

import com.github.chaosfirebolt.mapper.configuration.annotation.Access;
import com.github.chaosfirebolt.mapper.configuration.annotation.Collection;
import com.github.chaosfirebolt.mapper.configuration.annotation.Mapped;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChaosFire on 29-Aug-18
 */
@Mapped
public class TeacherView extends PersonView {

    @Access(getterName = "getStudents", setterName = "setStudents", setterParams = List.class)
    @Collection(elementType = StudentView.class)
    private List<StudentView> students;

    public TeacherView() {
        this.students = new ArrayList<>();
    }

    public List<StudentView> getStudents() {
        return this.students;
    }

    public void setStudents(List<StudentView> students) {
        this.students = students;
    }
}