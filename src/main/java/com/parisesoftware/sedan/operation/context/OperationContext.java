package com.parisesoftware.sedan.operation.context;

import com.parisesoftware.sedan.operation.OperationType;

public record OperationContext(OperationType type, String name, Object value) {}
