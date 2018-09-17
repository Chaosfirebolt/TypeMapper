package com.github.chaosfirebolt.mapper;

import com.github.chaosfirebolt.mapper.constant.Mapper;

/**
 * Created by ChaosFire on 17-Sep-18
 */
public class AnnotationTypeMapperTests extends AbstractTypeMapperTests {

    public AnnotationTypeMapperTests() {
        super(MapperFactory.getMapper(Mapper.ANNOTATION));
    }
}