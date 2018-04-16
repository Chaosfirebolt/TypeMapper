package com.github.chaosfirebolt.mapper.exc;

/**
 * Created by ChaosFire on 11-Apr-18
 */
public class MappingException extends RuntimeException {

    public MappingException(String message) {
        super(message);
    }

    public MappingException(String message, Throwable cause) {
        super(message, cause);
    }
}