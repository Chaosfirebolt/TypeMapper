package com.github.chaosfirebolt.mapper.dummy;

import com.github.chaosfirebolt.mapper.configuration.annotation.Access;
import com.github.chaosfirebolt.mapper.configuration.annotation.Mapped;

/**
 * Created by ChaosFire on 29-Aug-18
 */
@Mapped
public class AdultView extends PersonView {

    @Access(getter = false, setterName = "setFriend", setterParams = AdultView.class)
    private AdultView friend;

    public AdultView getFriend() {
        return this.friend;
    }

    public void setFriend(AdultView friend) {
        this.friend = friend;
    }
}