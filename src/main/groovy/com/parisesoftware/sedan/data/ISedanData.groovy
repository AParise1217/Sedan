package com.parisesoftware.sedan.data

/**
 * Encapsulation of some data that the Sedan Algorithm can operate on
 */
interface ISedanData {

    /**
     * Fetch the value located at the given key
     * @param key       the key of the map to fetch for
     * @return {@code Object}
     */
    Object getValueAt(Object key)

    /**
     * Fetches all the keys in the param source
     * @return {@code Set}
     */
    Set getKeys()

    /**
     * Check if the map contains the key
     * @param key       the key to check
     * @return {@code boolean}
     */
    boolean containsKey(Object key)

}
