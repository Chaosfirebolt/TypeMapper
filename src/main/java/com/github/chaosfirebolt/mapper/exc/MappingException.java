package com.github.chaosfirebolt.mapper.exc;

/**
 * This exception signifies error during mapping or transformation process of objects.
 *
 * Created by ChaosFire on 11-Apr-18
 */
public class MappingException extends RuntimeException {

    /**
     * Constructs exception with specified error message.
     *
     * @param message Detailed error message.
     */
    public MappingException(String message) {
        super(message);
    }

    /**
     * Constructs exception with specified error message and cause for the exception.
     *
     * @param message Detailed error message.
     * @param cause The cause for this exception.
     */
    public MappingException(String message, Throwable cause) {
        super(message, cause);
    }
}