package com.github.chaosfirebolt.mapper.configuration;

import com.github.chaosfirebolt.mapper.configuration.action.Action;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Created by ChaosFire on 11-Apr-18
 */
class MappingImpl<S, D> implements Mapping<S, D> {

    private final List<Action<S, D>> actions;

    MappingImpl() {
        this.actions = new LinkedList<>();
    }

    @Override
    public Composer<S, D, ?, ?> composer() {
        return new ComposerImpl<>(this);
    }

    @Override
    public List<Action<S, D>> getActions() {
        return Collections.unmodifiableList(this.actions);
    }

    @Override
    public void register(Action<S, D> action) {
        this.actions.add(Objects.requireNonNull(action));
    }
}