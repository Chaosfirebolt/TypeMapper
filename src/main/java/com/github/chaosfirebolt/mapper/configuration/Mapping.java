package com.github.chaosfirebolt.mapper.configuration;

import com.github.chaosfirebolt.mapper.configuration.action.Action;

import java.util.List;

/**
 * Interface holding required data for object transformation.
 *
 * @param <S> The type of source objects.
 * @param <D> The type of destination objects.
 *
 * Created by ChaosFire on 12-Apr-18
 */
public interface Mapping<S, D> {

    /**
     * Creates and returns new Composer. See {@link Composer}.
     *
     * @return New Composer.
     */
    Composer<S, D, ?, ?> composer();

    /**
     * Returns actions registered for this mapping.
     *
     * @return List of {@link Action}
     */
    List<Action<S, D>> getActions();

    /**
     * Registers an action for the mapping.
     *
     * @param action {@link Action} to add to registered actions for this mapping.
     */
    void register(Action<S, D> action);

    /**
     * Returns {@link Mapping} registered as parent for this one.
     *
     * @return Parent {@link Mapping} of this mapping or null, if parent mapping is not registered or does not exist.
     */
    Mapping<? super S, ? super D> getParent();
}