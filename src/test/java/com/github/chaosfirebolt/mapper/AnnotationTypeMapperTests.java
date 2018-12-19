package com.github.chaosfirebolt.mapper;

import com.github.chaosfirebolt.mapper.constant.Mapper;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by ChaosFire on 17-Sep-18
 */
public class AnnotationTypeMapperTests extends AbstractTypeMapperTests {

    public AnnotationTypeMapperTests() {
        super(MapperFactory.getMapper(Mapper.ANNOTATION));
    }

    @Test
    @Ignore("Such mapping cannot be done with AnnotationTypeMapper.")
    @Override
    public void mapChildrenClasses_DestinationObject_ShouldReturnCorrectlyFilledObject() {
        super.mapChildrenClasses_DestinationObject_ShouldReturnCorrectlyFilledObject();
    }
}