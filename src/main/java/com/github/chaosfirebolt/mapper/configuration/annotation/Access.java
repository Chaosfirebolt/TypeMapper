package com.github.chaosfirebolt.mapper.configuration.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by ChaosFire on 11-May-18
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Access {

    boolean getter() default true;
    String getterName() default "";
    boolean setter() default true;
    String setterName() default "";
    Class<?>[] setterParams() default {};
    String from() default "";
}