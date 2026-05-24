package com.parisesoftware.sedan.operation.context;

import com.parisesoftware.sedan.operation.AdditionSedanOperation;
import com.parisesoftware.sedan.operation.DeletionSedanOperation;
import com.parisesoftware.sedan.operation.OperationType;
import com.parisesoftware.sedan.operation.SedanOperation;
import com.parisesoftware.sedan.operation.UpdateSedanOperation;

import java.util.Objects;

public record OperationContext(OperationType type, String name, Object value) {

    /**
     * Constructs the {@link SedanOperation} described by this context.
     * The switch expression is exhaustive — adding a new {@link OperationType} without a case here
     * will be a compile error.
     */
    public SedanOperation toOperation() {
        Objects.requireNonNull(type, "Cannot create operation from context with null type");
        return switch (type) {
            case ADD    -> new AdditionSedanOperation(name, value);
            case UPDATE -> new UpdateSedanOperation(name, value);
            case DELETE -> new DeletionSedanOperation(name);
        };
    }
}
