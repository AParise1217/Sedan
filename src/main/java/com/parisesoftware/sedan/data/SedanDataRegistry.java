package com.parisesoftware.sedan.data;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Thread-safe registry for {@link SedanDataAdapter} implementations.
 * Uses {@link CopyOnWriteArrayList} so iteration during {@link #adapt} never races with
 * concurrent {@link #registerAdapter} calls.
 */
public class SedanDataRegistry {

    private final List<SedanDataAdapter<?>> adapters = new CopyOnWriteArrayList<>();

    public SedanDataRegistry registerAdapter(SedanDataAdapter<?> adapter) {
        if (adapter == null) throw new IllegalArgumentException("Adapter cannot be null");
        adapters.add(adapter);
        return this;
    }

    public SedanDataSource adapt(Object source) {
        if (source == null) throw new IllegalArgumentException("Source data cannot be null");
        return adapters.stream()
                .filter(a -> a.supports(source.getClass()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "No adapter found for type: " + source.getClass().getName()))
                .adaptObject(source);
    }

    public boolean hasAdapterFor(Class<?> sourceType) {
        if (sourceType == null) throw new IllegalArgumentException("Source type cannot be null");
        return adapters.stream().anyMatch(a -> a.supports(sourceType));
    }

    public int getAdapterCount() {
        return adapters.size();
    }
}
