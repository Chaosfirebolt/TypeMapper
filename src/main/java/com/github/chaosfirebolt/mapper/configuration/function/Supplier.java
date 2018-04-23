package com.github.chaosfirebolt.mapper.configuration.function;

/**
 * Represents data supplier, accepting an argument and returning data from it.
 *
 * @param <SupplierObject> Type of the object supplying data.
 * @param <Result> Type of the result.
 *
 * Created by ChaosFire on 11-Apr-18
 */
@FunctionalInterface
public interface Supplier<SupplierObject, Result> {

    /**
     * Supplies data from given object.
     *
     * @param supplierObject Object to retrieve data from.
     * @return data retrieved from object.
     */
    Result supply(SupplierObject supplierObject);
}