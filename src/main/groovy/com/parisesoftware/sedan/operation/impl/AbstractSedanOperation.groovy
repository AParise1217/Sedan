package com.parisesoftware.sedan.operation.impl

import com.parisesoftware.sedan.operation.ISedanOperation
import com.parisesoftware.sedan.operation.OperationType

/**
 * {@inheritDoc}
 *
 * Base Class for behavior common to all Sedan Operations
 */
abstract class AbstractSedanOperation implements ISedanOperation {

    protected final OperationType type

    protected final Object name

    AbstractSedanOperation(Object name, OperationType type) {
        this.name = name
        this.type = type
    }

    /**
     * {@inheritDoc}
     */
    @Override
    abstract String toString()

    /**
     * {@inheritDoc}
     */
    @Override
    OperationType getType() {
        return this.type
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Object getName() {
        return this.name
    }

}
