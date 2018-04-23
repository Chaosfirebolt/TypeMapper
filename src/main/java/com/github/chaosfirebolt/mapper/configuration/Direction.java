package com.github.chaosfirebolt.mapper.configuration;

/**
 * Convenience class representing direction of object to object transformation.
 *
 * @param <S> Type of source objects.
 * @param <D> Type of destination objects.
 *
 * Created by ChaosFire on 11-Apr-18
 */
public class Direction<S, D> {

    /**
     * Source class.
     */
    private final Class<S> sourceClass;
    /**
     * Destination class.
     */
    private final Class<D> destinationClass;

    public Direction(Class<S> sourceClass, Class<D> destinationClass) {
        this.sourceClass = sourceClass;
        this.destinationClass = destinationClass;
    }

    Class<S> getSourceClass() {
        return this.sourceClass;
    }

    Class<D> getDestinationClass() {
        return this.destinationClass;
    }

    @Override
    public int hashCode() {
        return 17 * this.sourceClass.hashCode() + 31 * this.destinationClass.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Direction)) {
            return false;
        }
        Direction other = (Direction) obj;
        return this.sourceClass.equals(other.sourceClass) && this.destinationClass.equals(other.destinationClass);
    }

    @Override
    public String toString() {
        return String.format("%s -> %s", this.sourceClass.getSimpleName(), this.destinationClass.getSimpleName());
    }
}