package com.github.chaosfirebolt.mapper.dummy;

import com.github.chaosfirebolt.mapper.configuration.annotation.Access;
import com.github.chaosfirebolt.mapper.configuration.annotation.Mapped;

/**
 * Created by ChaosFire on 29-Aug-18
 */
@Mapped
public class Adult extends Person {

    @Access(getterName = "getFriend", setter = false)
    private Adult friend;

    public Adult(String name, int age, String job) {
        super(name, age, job);
    }

    public Adult(String name, int age, String job, Adult friend) {
        super(name, age, job);
        this.friend = friend;
    }

    public Adult getFriend() {
        return this.friend;
    }

    public void setFriend(Adult friend) {
        this.friend = friend;
    }
}