package com.parisesoftware.sedan.operation

/**
 * Abstraction for a Sedan Operational Context
 */
interface IOperationContext {

    Object getName()

    Object getValue()

    OperationType getType()

}
