package com.github.chaosfirebolt.mapper.configuration.action;

import com.github.chaosfirebolt.mapper.dummy.Dto;
import com.github.chaosfirebolt.mapper.dummy.Entity;
import com.github.chaosfirebolt.mapper.exc.MappingException;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by ChaosFire on 3.5.2018 г.
 */
public class ActionFactoryTests {

    public ActionFactoryTests() {
    }

    @Test(expected = MappingException.class)
    public void noSupplierProvided_ShouldThrowException() {
        ActionFactory.createAction(null, Dto::setAge, Object::toString, Object.class, String.class);
    }

    @Test(expected = MappingException.class)
    public void noConsumerProvided_ShouldThrowException() {
        ActionFactory.createAction(Entity::getAge, null, null, Integer.class, Object.class);
    }

    @Test(expected = MappingException.class)
    public void differentIOClasses_NoTransformingFunc_ShouldThrowException() {
        ActionFactory.createAction(Entity::getAge, Dto::setAge, null, Integer.class, String.class);
    }

    @Test
    public void sameIOClasses_NoTransformingFunction_ShouldReturnAction() {
        Action<Entity, Dto> action = ActionFactory.createAction(en -> en.getFirstName() + " " + en.getLastName(), Dto::setFullName, null,
                String.class, String.class);
        assertNotNull(action);
    }

    @Test
    public void sameIOClasses_ValidFunctions_ShouldReturnCorrectImplementation() {
        Action<Entity, Dto> action = ActionFactory.createAction(en -> en.getFirstName() + " " + en.getLastName(), Dto::setFullName, null,
                String.class, String.class);
        assertTrue(action instanceof DirectAction);
    }

    @Test
    public void diffIOClasses_ValidFunctions_ShouldReturnAction() {
        Action<Entity, Dto> action = ActionFactory.createAction(Entity::getAge, Dto::setAge, Object::toString, Integer.class, String.class);
        assertNotNull(action);
    }

    @Test
    public void diffIOClasses_ValidFunctions_ShouldReturnCorrectImplementation() {
        Action<Entity, Dto> action = ActionFactory.createAction(Entity::getAge, Dto::setAge, Object::toString, Integer.class, String.class);
        assertTrue(action instanceof TransformingAction);
    }
}