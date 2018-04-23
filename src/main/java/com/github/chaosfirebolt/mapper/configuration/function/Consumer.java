package com.github.chaosfirebolt.mapper.configuration.function;

/**
 * Represents data consumer, setting supplied data in supplied object.
 *
 * @param <ConsumerObject> Type of supplied object.
 * @param <InputData> Type of supplied data.
 *
 * Created by ChaosFire on 11-Apr-18
 */
@FunctionalInterface
public interface Consumer<ConsumerObject, InputData> {

    /**
     * Sets supplied data into consumer object.
     *
     * @param consumerObject Object to set data into.
     * @param inputData Data.
     */
    void consume(ConsumerObject consumerObject, InputData inputData);
}