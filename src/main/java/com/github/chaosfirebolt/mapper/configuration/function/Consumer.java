package com.github.chaosfirebolt.mapper.configuration.function;

/**
 * Created by ChaosFire on 11-Apr-18
 */
@FunctionalInterface
public interface Consumer<ConsumerObject, InputData> {

    void consume(ConsumerObject consumerObject, InputData inputData);
}