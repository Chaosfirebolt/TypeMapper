package com.github.chaosfirebolt.mapper.configuration.action;

import com.github.chaosfirebolt.mapper.configuration.function.Consumer;
import com.github.chaosfirebolt.mapper.configuration.function.Supplier;

import java.util.function.Function;

/**
 * Created by ChaosFire on 11-Apr-18
 */
class TransformingAction<S, D, From, To> implements Action<S, D> {

    private final Supplier<S, From> supplier;
    private final Consumer<D, To> consumer;
    private final Function<From, To> function;

    TransformingAction(Supplier<S, From> supplier, Consumer<D, To> consumer, Function<From, To> function) {
        this.supplier = supplier;
        this.consumer = consumer;
        this.function = function;
    }

    @Override
    public void perform(S sourceObject, D destinationObject) {
        this.consumer.consume(destinationObject, this.function.apply(this.supplier.supply(sourceObject)));
    }
}