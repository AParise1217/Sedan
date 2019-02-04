package com.parisesoftware.sedan.operation.context

import com.parisesoftware.sedan.operation.IOperationContext
import com.parisesoftware.sedan.operation.OperationType

/**
 * {@inheritDoc}
 *
 * Data Transfer Object for passing the Operational Context between classes
 */
class OperationContext implements IOperationContext {

    OperationType type

    String name

    Object value

}
