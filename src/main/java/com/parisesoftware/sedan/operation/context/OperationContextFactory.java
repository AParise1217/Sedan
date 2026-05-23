package com.parisesoftware.sedan.operation.context;

import com.google.common.base.Preconditions;
import com.parisesoftware.sedan.operation.OperationType;

public class OperationContextFactory {

    public static OperationContext createDeleteContext(Object name) {
        Preconditions.checkArgument(name instanceof String, "name must be a String");
        return new OperationContext(OperationType.DELETE, (String) name, null);
    }

    public static OperationContext createUpdateContext(Object name, Object value) {
        Preconditions.checkArgument(name instanceof String, "name must be a String");
        return new OperationContext(OperationType.UPDATE, (String) name, value);
    }

    public static OperationContext createAddContext(Object name, Object value) {
        Preconditions.checkArgument(name instanceof String, "name must be a String");
        return new OperationContext(OperationType.ADD, (String) name, value);
    }
}
