package com.github.chaosfirebolt.mapper.configuration;

import com.github.chaosfirebolt.mapper.configuration.action.Action;
import com.github.chaosfirebolt.mapper.configuration.action.ActionFactory;
import com.github.chaosfirebolt.mapper.configuration.function.Consumer;
import com.github.chaosfirebolt.mapper.configuration.function.Supplier;
import com.github.chaosfirebolt.mapper.constant.Mapper;

import java.util.function.Function;

/**
 * Created by ChaosFire on 11-Apr-18
 */
class ComposerImpl<S, D, SR, DI> implements Composer<S, D, SR, DI> {

    private final Mapping<S, D> mapping;
    private final Mapper mapper;
    private Class<SR> sourceFieldClass;
    private Class<DI> destinationFieldClass;
    private Supplier<S, SR> supplier;
    private Consumer<D, DI> consumer;
    private Function<SR, DI> function;

    ComposerImpl(Mapping<S, D> mapping, Mapper mapper) {
        this.mapper = mapper;
        this.mapping = mapping;
    }

    private ComposerImpl(Mapping<S, D> mapping, Mapper mapper, Class<SR> sourceFieldClass, Class<DI> destinationFieldClass) {
        this.mapper = mapper;
        this.mapping = mapping;
        this.sourceFieldClass = sourceFieldClass;
        this.destinationFieldClass = destinationFieldClass;
    }

    @Override
    public <From, To> Composer<S, D, From, To> transform(Class<From> fromClass, Class<To> toClass) {
        return new ComposerImpl<>(this.mapping, this.mapper, fromClass, toClass);
    }

    @Override
    public Composer<S, D, SR, DI> supplier(Supplier<S, SR> supplier) {
        this.supplier = supplier;
        return this;
    }

    @Override
    public Composer<S, D, SR, DI> consumer(Consumer<D, DI> consumer) {
        this.consumer = consumer;
        return this;
    }

    @Override
    public Composer<S, D, SR, DI> function(Function<SR, DI> function) {
        this.function = function;
        return this;
    }

    @Override
    public Composer<S, D, ?, ?> compose() {
        Action<S, D> action = ActionFactory.createAction(this.supplier, this.consumer, this.function, this.sourceFieldClass, this.destinationFieldClass);
        this.mapping.register(action);
        return new ComposerImpl<>(this.mapping, this.mapper);
    }

    @Override
    public Configuration finish() {
        Action<S, D> action = ActionFactory.createAction(this.supplier, this.consumer, this.function, this.sourceFieldClass, this.destinationFieldClass);
        this.mapping.register(action);
        return ConfigurationFactory.getConfiguration(this.mapper);
    }
}