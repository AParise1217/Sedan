package com.parisesoftware.sedan.operation.context;

import com.parisesoftware.sedan.operation.OperationType;

public class OperationContextFactory {

    public static OperationContext createDeleteContext(Object name) {
        if (!(name instanceof String s)) throw new IllegalArgumentException("name must be a String");
        return new OperationContext(OperationType.DELETE, s, null);
    }

    public static OperationContext createUpdateContext(Object name, Object value) {
        if (!(name instanceof String s)) throw new IllegalArgumentException("name must be a String");
        return new OperationContext(OperationType.UPDATE, s, value);
    }

    public static OperationContext createAddContext(Object name, Object value) {
        if (!(name instanceof String s)) throw new IllegalArgumentException("name must be a String");
        return new OperationContext(OperationType.ADD, s, value);
    }
}
