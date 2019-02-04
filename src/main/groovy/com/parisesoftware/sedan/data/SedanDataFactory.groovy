package com.parisesoftware.sedan.data

import com.parisesoftware.sedan.data.impl.SedanDataMapAdapter
import org.apache.commons.lang3.NotImplementedException

/**
 * Factory for creating instances of {@link ISedanData}
 */
class SedanDataFactory {

    /**
     * Constructs an ISedanData instance out of a source object
     * @param source
     * @return
     */
    static ISedanData construct(Object source) {

        if(source instanceof Map) {
            return new SedanDataMapAdapter(source as Map)
        }

        throw new NotImplementedException("There is no implementation of ISedanData for provided type ${source.class}")
    }

}
