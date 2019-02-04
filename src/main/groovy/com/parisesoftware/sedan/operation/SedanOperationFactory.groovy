package com.parisesoftware.sedan.operation

import com.parisesoftware.sedan.operation.impl.AdditionSedanOperation
import com.parisesoftware.sedan.operation.impl.DeletionSedanOperation
import com.parisesoftware.sedan.operation.impl.UpdateSedanOperation
import org.apache.commons.lang3.NotImplementedException

/**
 * Abstract Factory to Handle Creating {@link ISedanOperation} instances
 */
class SedanOperationFactory {

    static ISedanOperation construct(IOperationContext context) {

        switch(context.type) {
            case OperationType.ADD:
                return new AdditionSedanOperation(context.name, context.value)
            case OperationType.UPDATE:
                return new UpdateSedanOperation(context.name, context.value)
            case OperationType.DELETE:
                return new DeletionSedanOperation(context.name)
            default:
                throw new NotImplementedException("No Sedan Operation implementation found for type `${context.type}`.")
        }

    }

}
