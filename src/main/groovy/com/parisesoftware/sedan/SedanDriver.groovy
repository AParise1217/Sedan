package com.parisesoftware.sedan

/**
 * Main Entry-Point point for the Sedan Algorithm
 */
class SedanDriver {

    /**
     * Sedan Difference Algorithm
     * @param source
     * @param target
     * @return
     */
    List difference(Map source, Map target) {
        List result = []

        getKeys(source).each { key ->
            if (!containsKey(target, key)) {
                result.add([operation: OperationType.DELETE, name: key])
            } else {
                if(hasDifferentValueAtKey(source, target, key)) {
                    result.add([operation: OperationType.UPDATE, name: key, value: target[key]])
                } else {
                    // if there is the same value at the key then do nothing
                }
            }
        }

        getKeys(target).each { key ->
            if(!containsKey(source, key)) {
                result.add([operation: OperationType.ADD, name: key, value: target[key]])
            } else {
                // if they both contain the key, then do nothing
            }
        }
        return result
    }

    /**
     * Check if there is a different value located at param key for each of the maps
     * @param source    the first map
     * @param target    the second map
     * @param key       the key
     * @return {@code boolean}
     */
    boolean hasDifferentValueAtKey(Map source, Map target, Object key) {
        return (getValueAt(source, key) != getValueAt(target, key))
    }

    /**
     * Fetch the value located at the given key
     * @param source    the map to query
     * @param key       the key of the map to fetch for
     * @return {@code Object}
     */
    Object getValueAt(Map source, Object key) {
        if(source == null) {
            return null
        }

        return source[key]
    }

    /**
     * Fetches all the keys in the param source
     * @param source    the map to query for the Keys
     * @return {@code Set}
     */
    Set getKeys(Map source) {
        if(source == null) {
            return []
        }

        return source.keySet()
    }

    /**
     * Check if the map contains the key
     * @param source    the source map to check
     * @param key       the key to check
     * @return {@code boolean}
     */
    boolean containsKey(Map source, Object key) {
        return source.containsKey(key)
    }

}
