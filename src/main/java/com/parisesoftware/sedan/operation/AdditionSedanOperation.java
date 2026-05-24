package com.parisesoftware.sedan.operation;

import java.util.Objects;

/** Represents a key that exists in the target but not in the source. */
public record AdditionSedanOperation(String name, Object value) implements SedanOperation {

    public AdditionSedanOperation {
        Objects.requireNonNull(name, "name must not be null");
    }

    @Override
    public OperationType type() {
        return OperationType.ADD;
    }
}
