package com.github.chaosfirebolt.mapper;

import com.github.chaosfirebolt.mapper.configuration.Mapping;
import com.github.chaosfirebolt.mapper.configuration.action.Action;

/**
 * Created by ChaosFire on 13-Sep-18
 */
class MapperUtil {

    static <S, D> void performActions(S source, D destination, Mapping<S, D> mapping) {
        if (mapping == null) {
            return;
        }
        performActions(source, destination, mapping.getParent());
        for (Action<S, D> action : mapping.getActions()) {
            action.perform(source, destination);
        }
    }
}