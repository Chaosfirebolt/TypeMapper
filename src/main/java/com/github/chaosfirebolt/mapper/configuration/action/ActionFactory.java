package com.github.chaosfirebolt.mapper.configuration.action;

import com.github.chaosfirebolt.mapper.configuration.function.Consumer;
import com.github.chaosfirebolt.mapper.configuration.function.Supplier;
import com.github.chaosfirebolt.mapper.exc.MappingException;

import java.util.function.Function;

/**
 * Factory creating {@link Action} objects.
 *
 * Created by ChaosFire on 16-Apr-18
 */
public class ActionFactory {

    /**
     * Creates and returns {@link Action}.
     *
     * @param supplier Supplier function.
     * @param consumer Consumer function.
     * @param function Transformation function.
     * @param sfClass Class of result returned by {@code supplier}.
     * @param dfClass Class of input accepted by {@code consumer}.
     * @param <S> Type of source object.
     * @param <D> Type of destination object.
     * @param <SF> Type of source field.
     * @param <DF> Type of destination field.
     * @return An instance of {@link Action}.
     * @throws MappingException In case of invalid arguments.
     */
    @SuppressWarnings("unchecked")
    public static <S, D, SF, DF> Action<S, D> createAction(Supplier<S, SF> supplier, Consumer<D, DF> consumer, Function<SF, DF> function, Class<SF> sfClass, Class<DF> dfClass) {
        if (supplier == null || consumer == null) {
            throw new MappingException("Supplier and consumer can't be null.");
        }
        Action<S, D> action;
        if (function != null) {
            action = new TransformingAction<>(supplier, consumer, function);
        } else {
            if (!sfClass.equals(dfClass)) {
                throw new MappingException("Missing transformation function.");
            }
            action = new DirectAction<>(supplier, (Consumer<D, SF>) consumer);
        }
        return action;
    }
}