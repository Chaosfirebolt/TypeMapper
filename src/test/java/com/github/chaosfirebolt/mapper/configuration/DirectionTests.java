package com.github.chaosfirebolt.mapper.configuration;

import com.github.chaosfirebolt.mapper.dummy.Dto;
import com.github.chaosfirebolt.mapper.dummy.Entity;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by ChaosFire on 6.5.2018 г.
 */
public class DirectionTests {

    private Direction<Entity, Dto> direction;
    private Direction<Entity, Dto> copy;

    public DirectionTests() {
        this.direction = new Direction<>(Entity.class, Dto.class);
        this.copy = new Direction<>(Entity.class, Dto.class);
    }

    @Test
    public void sameDirection_ShouldReturnSameHashCode() {
        assertEquals(this.direction.hashCode(), this.copy.hashCode());
    }

    @Test
    public void sameDirection_EqualsShouldReturnTrue() {
        assertEquals(this.direction, this.copy);
    }

    @Test
    public void reverseDirection_EqualsShouldReturnFalse() {
        Direction<Dto, Entity> reverse = new Direction<>(Dto.class, Entity.class);
        assertNotEquals(this.direction, reverse);
    }
}