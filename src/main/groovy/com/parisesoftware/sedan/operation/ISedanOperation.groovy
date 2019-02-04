package com.parisesoftware.sedan.operation

/**
 * Abstraction for a Sedan Operation
 */
interface ISedanOperation {

    String toString()

    /**
     * Fetch the Type of the Operation
     * @return {@code OperationType}
     */
    OperationType getType()

    /**
     * Fetch the Name of the Key the Operation Corresponds to
     * @return {@code Object}
     */
    Object getName()

}
