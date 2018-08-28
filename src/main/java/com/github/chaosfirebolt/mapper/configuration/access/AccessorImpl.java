package com.github.chaosfirebolt.mapper.configuration.access;

/**
 * Created by ChaosFire on 14-May-18
 */
class AccessorImpl implements Accessor {

    private final Getter getter;
    private final Setter setter;

    AccessorImpl(Getter getter, Setter setter) {
        this.getter = getter;
        this.setter = setter;
    }

    @Override
    public Getter getter() {
        return this.getter;
    }

    @Override
    public Setter setter() {
        return this.setter;
    }
}