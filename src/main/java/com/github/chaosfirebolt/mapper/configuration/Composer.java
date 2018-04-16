package com.github.chaosfirebolt.mapper.configuration;

import com.github.chaosfirebolt.mapper.configuration.function.Consumer;
import com.github.chaosfirebolt.mapper.configuration.function.Supplier;

import java.util.function.Function;

/**
 * Created by ChaosFire on 11-Apr-18
 */
public interface Composer<S, D, SR, DI> {

    <From, To> Composer<S, D, From, To> transform(Class<From> fromClass, Class<To> toClass);
    Composer<S, D, SR, DI> supplier(Supplier<S, SR> supplier);
    Composer<S, D, SR, DI> consumer(Consumer<D, DI> consumer);
    Composer<S, D, SR, DI> function(Function<SR, DI> function);
    Composer<S, D, ?, ?> compose();
    Configuration finish();
}