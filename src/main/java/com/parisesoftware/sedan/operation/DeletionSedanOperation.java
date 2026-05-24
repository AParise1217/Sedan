package com.parisesoftware.sedan.operation;

import java.util.Objects;

/** Represents a key that exists in the source but not in the target. */
public record DeletionSedanOperation(String name) implements SedanOperation {

    public DeletionSedanOperation {
        Objects.requireNonNull(name, "name must not be null");
    }

    @Override
    public OperationType type() {
        return OperationType.DELETE;
    }

    @Override
    public Object value() {
        return null;
    }
}
