package com.github.chaosfirebolt.mapper.configuration;

/**
 * Entry point for manual configuration of type mapper.
 *
 * Created by ChaosFire on 12-Apr-18
 */
public interface Configuration {

    /**
     * Configuration for {@link com.github.chaosfirebolt.mapper.TypeMapper}
     *
     * @return Singleton implementation of the interface.
     */
    static Configuration getConfiguration() {
        return ConfigurationImpl.getInstance();
    }

    /**
     * Creates and returns new instance of {@link Mapping} or returns existing instance for the specified direction. If there is no such instance attempts to resolve parent mapping
     * for super classes of types <S> and <D>
     *
     * @param direction Direction of the mapping. See {@link Direction}
     * @param <S> Type of the source objects.
     * @param <D> Type of the destination objects.
     * @return Mapping for this direction.
     */
    <S, D> Mapping<S, D> mapping(Direction<S, D> direction);

    /**
     * Creates and returns new instance of {@link Mapping} or returns existing instance for the specified source and destination classes. If there is no such instance attempts to resolve
     * parent mapping for super classes of types <S> and <D>. Performed operation is analogous to operation performed by {@link Configuration#mapping(Direction)}
     *
     * @param sourceClass Class of the source objects.
     * @param destinationClass Class of the destination objects.
     * @param <S> Type of the source objects.
     * @param <D> Type of the destination objects.
     * @return Mapping for this direction.
     */
    <S, D> Mapping<S, D> mapping(Class<S> sourceClass, Class<D> destinationClass);

    /**
     * Creates and returns new instance of {@link Mapping} or returns existing instance for the specified direction. If there is no such instance sets the provided mapping as parent mapping
     * for the newly created one. See {@link Configuration#mapping(Direction)}
     *
     * @param direction Direction of the mapping. See {@link Direction}
     * @param parentMapping Mapping of super classes to be set as parent for this mapping.
     * @param <S> Type of the source objects.
     * @param <D> Type of the destination objects.
     * @return Mapping for this direction.
     */
    <S, D> Mapping<S, D> mapping(Direction<S, D> direction, Mapping<? super S, ? super D> parentMapping);

    /**
     * Creates and returns new instance of {@link Mapping} or returns existing instance for the specified source and destination classes. If there is no such instance sets provided
     * mapping as parent mapping for the newly created one. Performed operation is analogous to operation performed by {@link Configuration#mapping(Direction, Mapping)}
     * @param sourceClass Class of the source objects.
     * @param destinationClass Class of the destination objects.
     * @param parentMapping Mapping of super classes to be set as parent for this mapping.
     * @param <S> Type of source objects.
     * @param <D> Type of destination objects.
     * @return Mapping for this direction.
     */
    <S, D> Mapping<S, D> mapping(Class<S> sourceClass, Class<D> destinationClass, Mapping<? super S, ? super D> parentMapping);
}