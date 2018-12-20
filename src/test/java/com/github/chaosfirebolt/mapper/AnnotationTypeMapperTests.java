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
    @Ignore("This kind of mapping result cannot be achieved with AnnotationTypeMapper.")
    @Override
    public void mapChildrenClasses_DestinationObject_ShouldReturnCorrectlyFilledObject() {
        super.mapChildrenClasses_DestinationObject_ShouldReturnCorrectlyFilledObject();
    }

    @Test
    @Ignore("This kind of mapping result cannot be achieved with AnnotationTypeMapper.")
    @Override
    public void mapParentClasses_DestinationObject_ShouldReturnCorrectlyFilledObject() {
        super.mapParentClasses_DestinationObject_ShouldReturnCorrectlyFilledObject();
    }

    @Test
    @Ignore("This kind of mapping result cannot be achieved with AnnotationTypeMapper.")
    @Override
    public void mapParentClasses_DestinationClass_ShouldReturnCorrectlyFilledObject() {
        super.mapParentClasses_DestinationClass_ShouldReturnCorrectlyFilledObject();
    }

    @Test
    @Ignore("This kind of mapping result cannot be achieved with AnnotationTypeMapper.")
    @Override
    public void mapChildrenClasses_DestinationClass_ShouldReturnCorrectlyFilledObject() {
        super.mapChildrenClasses_DestinationClass_ShouldReturnCorrectlyFilledObject();
    }
}