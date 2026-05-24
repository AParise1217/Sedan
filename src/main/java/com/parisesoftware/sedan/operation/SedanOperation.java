package com.parisesoftware.sedan.operation;

/**
 * A single edit operation produced by the Sedan diff algorithm.
 *
 * The sealed hierarchy makes switch expressions over SedanOperation exhaustive at compile time —
 * adding a new OperationType without a corresponding permit will fail to compile.
 */
public sealed interface SedanOperation
        permits AdditionSedanOperation, DeletionSedanOperation, UpdateSedanOperation {

    OperationType type();

    String name();

    /**
     * The new value for ADD and UPDATE operations, or {@code null} for DELETE.
     */
    Object value();
}
