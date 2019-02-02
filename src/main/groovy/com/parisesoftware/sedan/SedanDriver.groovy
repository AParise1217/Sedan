package com.parisesoftware.sedan

class SedanDriver {

    static final String DELETE_OPERATION = 'delete'
    static final String UPDATE_OPERATION = 'update'
    static final String ADD_OPERATION = 'add'

    List difference(Map source, Map target) {
        List result = []

        source.keySet().each { key ->
            if (!containsKey(target, key)) {
                result.add([operation: DELETE_OPERATION, name: key])
            } else {
                if(hasDifferentValueAtKey(source, target, key)) {
                    result.add([operation: UPDATE_OPERATION, name: key, value: target[key]])
                } else {
                    // if there is the same value at the key then do nothing
                }
            }
        }

        target.keySet().each { key ->
            if(!containsKey(source, key)) {
                result.add([operation: ADD_OPERATION, name: key, value: target[key]])
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
        return (source[key] != target[key])
    }

    /**
     * Check if the map contains the key
     * @param target    the target map to check
     * @param key       the key to check
     * @return {@code boolean}
     */
    boolean containsKey(Map target, Object key) {
        return target.containsKey(key)
    }

}
