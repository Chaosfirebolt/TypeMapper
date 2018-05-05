package com.github.chaosfirebolt.mapper.configuration.action;

import com.github.chaosfirebolt.mapper.dummy.Dto;
import com.github.chaosfirebolt.mapper.dummy.Entity;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by ChaosFire on 4.5.2018 г.
 */
public class ActionTests {

    private final Action<Entity, Dto> transformAction;
    private final Action<Entity, Dto> directAction;
    private Entity entity;
    private Dto dto;

    public ActionTests() {
        this.transformAction = ActionFactory.createAction(Entity::getAge, Dto::setAge, Object::toString,
                Integer.class, String.class);
        this.directAction = ActionFactory.createAction(en -> en.getFirstName() + " " + en.getLastName(), Dto::setFullName, null,
                String.class, String.class);
    }

    @Before
    public void setUp() {
        this.entity = new Entity();
        this.entity.setFirstName("George");
        this.entity.setLastName("Bush");
        this.entity.setAge(20);
        this.dto = new Dto();
    }

    @Test
    public void directActionTest_ShouldReturnCorrectResult() {
        this.directAction.perform(this.entity, this.dto);
        String expected = this.entity.getFirstName() + " " + this.entity.getLastName();
        assertEquals(expected, this.dto.getFullName());
    }

    @Test
    public void transformActionTest_ShouldReturnCorrectResult() {
        this.transformAction.perform(this.entity, this.dto);
        String expected = Integer.toString(this.entity.getAge());
        assertEquals(expected, this.dto.getAge());
    }
}