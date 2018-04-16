package com.github.chaosfirebolt.mapper.constant;

/**
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