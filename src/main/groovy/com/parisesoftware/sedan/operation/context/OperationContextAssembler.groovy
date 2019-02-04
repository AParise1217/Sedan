package com.parisesoftware.sedan.operation.context

import com.parisesoftware.sedan.operation.IOperationContext
import com.parisesoftware.sedan.operation.OperationType

/**
 * Factory for {@link IOperationContext} instances
 */
class OperationContextAssembler {

    /**
     * Constructs a Deletion Context
     * @param name
     * @return {@code IOperationContext}
     */
    static IOperationContext createDeleteContext(Object name) {
        return new OperationContext(type: OperationType.DELETE, name: name)
    }

    /**
     * Constructs an Update Context
     * @param name
     * @param value
     * @return {@code IOperationContext}
     */
    static IOperationContext createUpdateContext(Object name, Object value) {
        return new OperationContext(type: OperationType.UPDATE, name: name, value: value)
    }

    /**
     * Constructs an Add Context
     * @param name
     * @param value
     * @return {@code IOperationContext}
     */
    static IOperationContext createAddContext(Object name, Object value) {
        return new OperationContext(type: OperationType.ADD, name: name, value: value)
    }

}
