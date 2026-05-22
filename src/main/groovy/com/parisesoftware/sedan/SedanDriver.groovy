package com.parisesoftware.sedan

import com.parisesoftware.sedan.data.SedanDataFactory
import com.parisesoftware.sedan.data.SedanDataSource
import com.parisesoftware.sedan.operation.ISedanOperation
import com.parisesoftware.sedan.operation.SedanOperationFactory
import com.parisesoftware.sedan.operation.context.OperationContextAssembler
import groovy.transform.PackageScope

/**
 * Main Entry-Point point for the Sedan Algorithm
 */
class SedanDriver {

    /**
     * Sedan Difference Algorithm
     * @param source
     * @param target
     * @return {@code ISedanOperation}
     */
    List<ISedanOperation> difference(Map source, Map target) {
        List<ISedanOperation> result = []
        SedanDataSource sourceData = SedanDataFactory.construct(source)
        SedanDataSource targetData = SedanDataFactory.construct(target)

        sourceData.getAllKeys().each { key ->
            if (!targetData.hasValue(key)) {
                result.add(createDeleteOperation(key))
            } else {
                if(hasDifferentValueAtKey(sourceData, targetData, key)) {
                    result.add(createUpdateOperation(key, targetData.getStringValue(key)))
                } else {
                    // if there is the same value at the key then do nothing
                }
            }
        }

        targetData.getAllKeys().each { key ->
            if(!sourceData.hasValue(key)) {
                result.add(createAddOperation(key, targetData.getStringValue(key)))
            } else {
                // if they both contain the key, then do nothing
            }
        }
        return result
    }

    /**
     * Simplifies the interaction for creating a new Update Operation
     * @param name  the name of the Key
     * @param value the value of the Key
     * @return {@code ISedanOperation}
     */
    @PackageScope
    static ISedanOperation createUpdateOperation(Object name, Object value) {
        return SedanOperationFactory.construct(OperationContextAssembler.createUpdateContext(name, value))
    }

    /**
     * Simplifies the interaction for creating a new Add Operation
     * @param name  the name of the Key
     * @param value the value of the Key
     * @return {@code ISedanOperation}
     */
    @PackageScope
    static ISedanOperation createAddOperation(Object name, Object value) {
        return SedanOperationFactory.construct(OperationContextAssembler.createAddContext(name, value))
    }

    /**
     * Simplifies the interaction for creating a new Delete Operation
     * @param name  the name of the Key
     * @return {@code ISedanOperation}
     */
    @PackageScope
    static ISedanOperation createDeleteOperation(Object name) {
        return SedanOperationFactory.construct(OperationContextAssembler.createDeleteContext(name))
    }

    /**
     * Check if there is a different value located at param key for each of the maps
     * @param source    the first map
     * @param target    the second map
     * @param key       the key
     * @return {@code boolean}
     */
    @PackageScope
    boolean hasDifferentValueAtKey(SedanDataSource source, SedanDataSource target, Object key) {
        return (source.getStringValue(key) != target.getStringValue(key))
    }

}