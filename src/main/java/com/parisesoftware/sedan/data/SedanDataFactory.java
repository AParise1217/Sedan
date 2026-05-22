package com.parisesoftware.sedan.data;

import com.parisesoftware.sedan.data.impl.MapSedanDataAdapter;

/**
 * Factory for creating instances of {@link SedanDataSource}
 * Uses a registry-based approach to support multiple data types
 */
public class SedanDataFactory {

    private static final SedanDataRegistry registry = new SedanDataRegistry();

    static {
        // Register default adapters
        registry.registerAdapter(new MapSedanDataAdapter());
    }

    /**
     * Get the registry for managing adapters
     * @return the adapter registry
     */
    public static SedanDataRegistry getRegistry() {
        return registry;
    }

    /**
     * Constructs a SedanDataSource instance from a source object
     * @param source the source data to adapt
     * @return a SedanDataSource implementation
     * @throws IllegalArgumentException if no adapter is available for the source type
     */
    public static SedanDataSource construct(Object source) {
        return registry.adapt(source);
    }

    /**
     * Check if the factory can handle the given source type
     * @param sourceType the type to check
     * @return true if an adapter is available
     */
    public static boolean canAdapt(Class<?> sourceType) {
        return registry.hasAdapterFor(sourceType);
    }
}