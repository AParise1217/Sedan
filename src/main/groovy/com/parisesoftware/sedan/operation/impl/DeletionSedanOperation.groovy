package com.parisesoftware.sedan.operation.impl

import com.parisesoftware.sedan.operation.OperationType
import org.apache.commons.lang3.builder.ToStringBuilder

class DeletionSedanOperation extends AbstractSedanOperation {

    private static final OperationType TYPE = OperationType.DELETE

    DeletionSedanOperation(Object name) {
        super(name, TYPE)
    }

    /**
     * {@inheritDoc}
     */
    @Override
    String toString() {
        return new ToStringBuilder(this)
                .append('type', type)
                .append('name', name)
                .toString()
    }
}
