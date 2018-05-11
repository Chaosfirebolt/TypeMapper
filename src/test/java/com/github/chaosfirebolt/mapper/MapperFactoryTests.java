package com.github.chaosfirebolt.mapper;

import com.github.chaosfirebolt.mapper.constant.Mapper;
import com.github.chaosfirebolt.mapper.testUtils.TestUtils;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

/**
 * Created by ChaosFire on 11-May-18
 */
public class MapperFactoryTests {

    private Mapper mapper;

    public MapperFactoryTests() {
        super();
        this.mapper = Mapper.MANUAL;
    }

    @After
    public void cleanup() {
        TestUtils.getMapperInstances().clear();
    }

    @Test
    public void noInstanceForMapper_NewInstanceShouldBeAdded() {
        MapperFactory.getMapper(this.mapper);
        assertNotNull(TestUtils.getMapperInstances().get(this.mapper));
    }

    @Test
    public void mapperInstanceExists_ShouldReturnSameInstance() {
        TypeMapper typeMapper = MapperFactory.getMapper(this.mapper);
        assertSame(typeMapper, MapperFactory.getMapper(this.mapper));
    }
}
