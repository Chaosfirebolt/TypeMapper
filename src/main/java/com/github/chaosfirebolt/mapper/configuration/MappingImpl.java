package com.github.chaosfirebolt.mapper.configuration;

import com.github.chaosfirebolt.mapper.configuration.action.Action;
import com.github.chaosfirebolt.mapper.constant.Mapper;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Created by ChaosFire on 11-Apr-18
 */
class MappingImpl<S, D> implements Mapping<S, D> {

    private final List<Action<S, D>> actions;
    private final Mapping<? super S, ? super D> parent;
    private final Mapper mapper;

    MappingImpl(Mapping<? super S, ? super D> parent, Mapper mapper) {
        this.actions = new LinkedList<>();
        this.parent = parent;
        this.mapper = mapper;
    }

    @Override
    public Composer<S, D, ?, ?> composer() {
        return new ComposerImpl<>(this, this.mapper);
    }

    @Override
    public List<Action<S, D>> getActions() {
        return Collections.unmodifiableList(this.actions);
    }

    @Override
    public void register(Action<S, D> action) {
        this.actions.add(Objects.requireNonNull(action));
    }

    @Override
    public Mapping<? super S, ? super D> getParent() {
        return this.parent;
    }
}