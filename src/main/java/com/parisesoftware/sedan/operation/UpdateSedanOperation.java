package com.parisesoftware.sedan.operation;

import java.util.Objects;

/** Represents a key that exists in both source and target but with a different value. */
public record UpdateSedanOperation(String name, Object value) implements SedanOperation {

    public UpdateSedanOperation {
        Objects.requireNonNull(name, "name must not be null");
    }

    @Override
    public OperationType type() {
        return OperationType.UPDATE;
    }
}
