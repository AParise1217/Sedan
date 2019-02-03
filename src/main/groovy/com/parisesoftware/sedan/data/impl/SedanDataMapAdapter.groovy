package com.parisesoftware.sedan.data.impl

import com.parisesoftware.sedan.data.ISedanData

/**
 * Adapts a Map data type to the {@link ISedanData} interface
 */
class SedanDataMapAdapter implements ISedanData {

    private final Map wrappedMap

    SedanDataMapAdapter(Map objectToBeWrapped) {
        this.wrappedMap = objectToBeWrapped
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Object getValueAt(Object key) {
        if(this.wrappedMap == null) {
            return null
        }

        return this.wrappedMap[key]
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Set getKeys() {
        if(this.wrappedMap == null) {
            return []
        }

        return this.wrappedMap.keySet()
    }

    /**
     * {@inheritDoc}
     */
    @Override
    boolean containsKey(Object key) {
        return this.wrappedMap.containsKey(key)
    }

}
