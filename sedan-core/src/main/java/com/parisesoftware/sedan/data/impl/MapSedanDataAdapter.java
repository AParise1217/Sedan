package com.parisesoftware.sedan.data.impl;

import com.parisesoftware.sedan.data.SedanDataAdapter;
import com.parisesoftware.sedan.data.SedanDataSource;
import java.util.*;

/**
 * Adapts a Map data type to the {@link SedanDataSource} interface
 */
public class MapSedanDataAdapter implements SedanDataAdapter<Map>, SedanDataSource {

    private final Map<String, Object> source;

    /**
     * Default constructor for registration with the factory
     */
    public MapSedanDataAdapter() {
        this.source = null;
    }

    /**
     * Constructor for creating an adapter instance
     * @param source the map to adapt
     */
    @SuppressWarnings("unchecked")
    public MapSedanDataAdapter(Map source) {
        this.source = Objects.requireNonNull(source, "Source map cannot be null");
    }

    @Override
    public boolean supports(Class<?> sourceType) {
        return Map.class.isAssignableFrom(sourceType);
    }

    @Override
    public SedanDataSource adapt(Map source) {
        return new MapSedanDataAdapter(source);
    }

    @Override
    public String getStringValue(String key) {
        if (source == null) return null;
        Object value = source.get(key);
        return value != null ? value.toString() : null;
    }

    @Override
    public Integer getIntegerValue(String key) {
        if (source == null) return null;
        Object value = source.get(key);
        if (value instanceof Integer) {
            return (Integer) value;
        }
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    @Override
    public Object getObjectValue(String key) {
        if (source == null) return null;
        return source.get(key);
    }

    @Override
    public boolean hasValue(String key) {
        return source != null && source.containsKey(key);
    }

    @Override
    public Set<String> getAllKeys() {
        if (source == null) {
            return Set.of();
        }
        return source.keySet();
    }

    @Override
    public int size() {
        if (source != null) {
            return source.size();
        }
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return source == null || source.isEmpty();
    }
}