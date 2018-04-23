package com.github.chaosfirebolt.mapper.configuration.action;

import com.github.chaosfirebolt.mapper.configuration.function.Consumer;
import com.github.chaosfirebolt.mapper.configuration.function.Supplier;

/**
 * Implementation of Action interface, which directly consumes result from supplier function.
 *
 * Created by ChaosFire on 11-Apr-18
 */
class DirectAction<S, D, F> implements Action<S, D> {

    private final Supplier<S, F> supplier;
    private final Consumer<D, F> consumer;

    DirectAction(Supplier<S, F> supplier, Consumer<D, F> consumer) {
        this.supplier = supplier;
        this.consumer = consumer;
    }

    @Override
    public void perform(S sourceObject, D destinationObject) {
        this.consumer.consume(destinationObject, this.supplier.supply(sourceObject));
    }
}