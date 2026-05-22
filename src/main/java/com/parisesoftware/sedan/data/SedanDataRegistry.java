package com.parisesoftware.sedan.data;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.Validate;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Thread-safe registry for managing SedanDataAdapter implementations
 * Uses only Guava for thread safety without explicit locks
 */
public class SedanDataRegistry {

    // Using AtomicReference for thread-safe updates to the adapter list
    private final AtomicReference<ImmutableList<SedanDataAdapter<?>>> adaptersRef =
            new AtomicReference<>(ImmutableList.of());

    // Using ConcurrentHashMap for thread-safe adapter lookup by class
    private final Map<Class<?>, SedanDataAdapter<?>> adapterByClass = new ConcurrentHashMap<>();

    /**
     * Register a new adapter with the registry
     * @param adapter the adapter to register
     * @return this registry instance for method chaining
     */
    public SedanDataRegistry registerAdapter(SedanDataAdapter<?> adapter) {
        Preconditions.checkArgument(adapter != null, "Adapter cannot be null");

        // Atomic update of the adapter list
        adaptersRef.updateAndGet(currentAdapters -> {
            List<SedanDataAdapter<?>> newList = Lists.newArrayList(currentAdapters);
            newList.add(adapter);
            return ImmutableList.copyOf(newList);
        });

        return this;
    }

    /**
     * Adapt source data to a SedanDataSource using the appropriate adapter
     * @param source the data to adapt
     * @return a SedanDataSource implementation
     * @throws IllegalArgumentException if no adapter is found for the source type
     */
    public SedanDataSource adapt(Object source) {
        Preconditions.checkArgument(source != null, "Source data cannot be null");

        // Get a snapshot of the current adapters list
        ImmutableList<SedanDataAdapter<?>> currentAdapters = adaptersRef.get();

        // Find the first adapter that supports the source type
        Optional<SedanDataAdapter<?>> adapter = currentAdapters.stream()
                .filter(a -> a.supports(source.getClass()))
                .findFirst();

        if (adapter.isPresent()) {
            // Cache the adapter for this class type for future lookups
            adapterByClass.put(source.getClass(), adapter.get());
            return adapter.get().adaptObject(source);
        }

        throw new IllegalArgumentException("No adapter found for type: " + source.getClass().getName());
    }

    /**
     * Check if the registry has an adapter for the given source type
     * @param sourceType the type to check
     * @return true if an adapter is available
     */
    public boolean hasAdapterFor(Class<?> sourceType) {
        Preconditions.checkArgument(sourceType != null, "Source type cannot be null");

        // First check the cache
        if (adapterByClass.containsKey(sourceType)) {
            return true;
        }

        // Fall back to checking all adapters
        ImmutableList<SedanDataAdapter<?>> currentAdapters = adaptersRef.get();
        return currentAdapters.stream().anyMatch(adapter -> adapter.supports(sourceType));
    }

    /**
     * Get the number of registered adapters
     * @return the adapter count
     */
    public int getAdapterCount() {
        return adaptersRef.get().size();
    }
}