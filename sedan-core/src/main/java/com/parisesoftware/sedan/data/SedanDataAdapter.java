package com.parisesoftware.sedan.data;

/**
 * Strategy interface for adapting different data types to SedanDataSource
 * @param <T> the type of source data this adapter can handle
 */
public interface SedanDataAdapter<T> {

    /**
     * Check if this adapter supports the given source type
     * @param sourceType the class to check
     * @return true if this adapter can handle the source type
     */
    boolean supports(Class<?> sourceType);

    /**
     * Adapt the source data to a SedanDataSource
     * @param source the data to adapt
     * @return a SedanDataSource implementation
     */
    SedanDataSource adapt(T source);

    /**
     * Adapt the source data to a SedanDataSource (non-generic version)
     * This method is used by the registry to handle type erasure
     * @param source the data to adapt
     * @return a SedanDataSource implementation
     * @throws IllegalArgumentException if the source type is not supported
     */
    @SuppressWarnings("unchecked")
    default SedanDataSource adaptObject(Object source) {
        if (!supports(source.getClass())) {
            throw new IllegalArgumentException("Adapter does not support type: " + source.getClass().getName());
        }
        return adapt((T) source);
    }
}