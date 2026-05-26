package com.parisesoftware.sedan.operation;

import java.util.Objects;

public record MoveSedanOperation(String sourceName, String targetName, Object value)
        implements SedanOperation {

    public MoveSedanOperation {
        Objects.requireNonNull(sourceName, "sourceName must not be null");
        Objects.requireNonNull(targetName, "targetName must not be null");
    }

    @Override
    public OperationType type() {
        return OperationType.MOVE;
    }

    @Override
    public String name() {
        return sourceName;
    }
}
