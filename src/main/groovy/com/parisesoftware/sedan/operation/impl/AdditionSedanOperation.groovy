package com.parisesoftware.sedan.operation.impl

import com.parisesoftware.sedan.operation.OperationType
import org.apache.commons.lang3.builder.ToStringBuilder

class AdditionSedanOperation extends AbstractSedanOperation {

    private static final OperationType TYPE = OperationType.ADD

    private final Object value

    AdditionSedanOperation(Object name, Object value) {
        super(name, TYPE)
        this.value = value
    }

    /**
     * {@inheritDoc}
     */
    @Override
    String toString() {
        return new ToStringBuilder(this)
                .append('type', type)
                .append('name', name)
                .append('value', value)
                .toString()
    }

}
