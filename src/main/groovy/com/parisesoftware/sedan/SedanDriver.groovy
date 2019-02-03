package com.parisesoftware.sedan

import com.parisesoftware.sedan.data.ISedanData
import com.parisesoftware.sedan.data.SedanDataFactory

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
        ISedanData sourceData = SedanDataFactory.construct(source)
        ISedanData targetData = SedanDataFactory.construct(target)

        sourceData.getKeys().each { key ->
            if (!targetData.containsKey(key)) {
                result.add([operation: OperationType.DELETE, name: key])
            } else {
                if(hasDifferentValueAtKey(sourceData, targetData, key)) {
                    result.add([operation: OperationType.UPDATE, name: key, value: targetData.getValueAt(key)])
                } else {
                    // if there is the same value at the key then do nothing
                }
            }
        }

        targetData.getKeys().each { key ->
            if(!sourceData.containsKey(key)) {
                result.add([operation: OperationType.ADD, name: key, value: targetData.getValueAt(key)])
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
    boolean hasDifferentValueAtKey(ISedanData source, ISedanData target, Object key) {
        return (source.getValueAt(key) != target.getValueAt(key))
    }

}
