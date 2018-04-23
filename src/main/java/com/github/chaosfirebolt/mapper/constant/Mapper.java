package com.github.chaosfirebolt.mapper.constant;

/**
 * Enumeration for implementations of {@link com.github.chaosfirebolt.mapper.TypeMapper}.
 *
 * Created by ChaosFire on 16-Apr-18
 */
public enum Mapper {

    MANUAL("Manual"),
    ANNOTATION("Annotation"),
    MIXED("Mixed");

    private String implementation;

    Mapper(String implementation) {
        this.implementation = implementation;
    }

    public String getImplementation() {
        return this.implementation;
    }
}