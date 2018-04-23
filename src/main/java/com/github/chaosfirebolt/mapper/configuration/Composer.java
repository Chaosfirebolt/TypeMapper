package com.github.chaosfirebolt.mapper.configuration;

import com.github.chaosfirebolt.mapper.configuration.function.Consumer;
import com.github.chaosfirebolt.mapper.configuration.function.Supplier;

import java.util.function.Function;

/**
 * This interface is used to declare rules for object to object transformation.
 *
 * @param <S> Type of source objects.
 * @param <D> Type of destination objects.
 * @param <SR> Type of field value extracted from a source object.
 * @param <DI> Type of field value to be set in destination object.
 *
 * Created by ChaosFire on 11-Apr-18
 */
public interface Composer<S, D, SR, DI> {

    /**
     * This method declares type of value extracted from source and type of value to be set in destination.
     *
     * @param fromClass Class of extracted value.
     * @param toClass Class of value to be set.
     * @param <From> Type of extracted value.
     * @param <To> Type of value to be set.
     * @return Instance of Composer with set class values for source and destination field.
     */
    <From, To> Composer<S, D, From, To> transform(Class<From> fromClass, Class<To> toClass);

    /**
     * This method declares a function supplying data from source object field/fields.
     *
     * @param supplier Supplying function - in most cases should be getter method.
     * @return This {@link Composer} with supplying function set.
     */
    Composer<S, D, SR, DI> supplier(Supplier<S, SR> supplier);

    /**
     * This method declares function consuming supplied data.
     *
     * @param consumer Consumer function - mostly a setter method.
     * @return This {@link Composer} with consuming function set.
     */
    Composer<S, D, SR, DI> consumer(Consumer<D, DI> consumer);

    /**
     * This method declares function transforming value of type <SR> to type <DI>. Setting this function is not necessary if
     * type <SR> is equal to type <DI>.
     * See {@link Composer#transform(Class, Class)}.
     *
     * @param function Function transforming object of type <SR> into object of type <DI>.
     * @return This {@link Composer} with transforming function set.
     */
    Composer<S, D, SR, DI> function(Function<SR, DI> function);

    /**
     * This method creates and registers an {@link com.github.chaosfirebolt.mapper.configuration.action.Action} from
     * declared {@link Supplier}, {@link Consumer} and {@link Function} and returns new {@link Composer} for this mapping.
     *
     * @return New instance of Composer for this mapping(<S> to <D>).
     */
    Composer<S, D, ?, ?> compose();

    /**
     * This method creates and registers an {@link com.github.chaosfirebolt.mapper.configuration.action.Action} from
     * declared {@link Supplier}, {@link Consumer} and {@link Function} and returns {@link Configuration}.
     * See {@link Composer#compose()}
     *
     * @return Configuration instance.
     */
    Configuration finish();
}