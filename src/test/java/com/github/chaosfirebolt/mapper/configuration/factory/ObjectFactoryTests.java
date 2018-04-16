package com.github.chaosfirebolt.mapper.configuration.factory;

import com.github.chaosfirebolt.mapper.dummy.InvalidCtorClass;
import com.github.chaosfirebolt.mapper.dummy.ValidCtorClass;
import com.github.chaosfirebolt.mapper.exc.MappingException;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by ChaosFire on 13-Apr-18
 */
public class ObjectFactoryTests {

    private ObjectFactory objectFactory;

    public ObjectFactoryTests() {
        this.objectFactory = new ObjectFactoryImpl();
    }

    @Test
    public void validCtor_ObjectShouldBeCreated() {
        ValidCtorClass validCtorClass = this.objectFactory.create(ValidCtorClass.class);
        assertNotNull(validCtorClass);
    }

    @Test(expected = MappingException.class)
    public void invalidCtor_ShouldThrowException() {
        this.objectFactory.create(InvalidCtorClass.class);
    }
}