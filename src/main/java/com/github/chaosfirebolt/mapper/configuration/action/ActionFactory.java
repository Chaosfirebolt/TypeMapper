package com.github.chaosfirebolt.mapper.configuration.action;

import com.github.chaosfirebolt.mapper.configuration.function.Consumer;
import com.github.chaosfirebolt.mapper.configuration.function.Supplier;
import com.github.chaosfirebolt.mapper.exc.MappingException;

import java.util.function.Function;

/**
 * Created by ChaosFire on 16-Apr-18
 */
public class ActionFactory {

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