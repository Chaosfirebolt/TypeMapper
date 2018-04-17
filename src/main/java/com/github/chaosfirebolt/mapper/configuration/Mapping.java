package com.github.chaosfirebolt.mapper.configuration;

import com.github.chaosfirebolt.mapper.configuration.action.Action;

import java.util.List;

/**
 * Created by ChaosFire on 12-Apr-18
 */
public interface Mapping<S, D> {

    Composer<S, D, ?, ?> composer();
    List<Action<S, D>> getActions();
    void register(Action<S, D> action);
    Mapping<? super S, ? super D> getParent();
}