package com.parisesoftware.sedan.data;

import java.util.Collection;
import java.util.Set;

/**
 * Encapsulation of some data that the Sedan Algorithm can operate on
 * Provides a domain-specific abstraction over various data sources
 */
public interface SedanDataSource {

    /**
     * Fetch the string value located at the given key
     * @param key the key of the map to fetch for
     * @return String value or null if not found
     */
    String getStringValue(String key);

    /**
     * Fetch the integer value located at the given key
     * @param key the key of the map to fetch for
     * @return Integer value or null if not found or not convertible
     */
    Integer getIntegerValue(String key);

    /**
     * Check if the data source contains the key
     * @param key the key to check
     * @return true if the key exists
     */
    boolean hasValue(String key);

    /**
     * Fetches all the keys in the data source
     * @return Collection of all keys
     */
    Set<String> getAllKeys();

    /**
     * Get the total number of key-value pairs
     * @return the size of the data source
     */
    int size();

    /**
     * Check if the data source is empty
     * @return true if no data is available
     */
    boolean isEmpty();
}