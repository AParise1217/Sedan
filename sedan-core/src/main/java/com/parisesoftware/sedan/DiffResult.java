package com.parisesoftware.sedan;

import com.parisesoftware.sedan.operation.AdditionSedanOperation;
import com.parisesoftware.sedan.operation.DeletionSedanOperation;
import com.parisesoftware.sedan.operation.MoveSedanOperation;
import com.parisesoftware.sedan.operation.SedanOperation;
import com.parisesoftware.sedan.operation.UpdateSedanOperation;

import java.util.List;

public record DiffResult(List<SedanOperation> operations) {

    public DiffResult {
        operations = List.copyOf(operations);
    }

    public List<AdditionSedanOperation> additions() {
        return operations.stream()
                .filter(AdditionSedanOperation.class::isInstance)
                .map(AdditionSedanOperation.class::cast)
                .toList();
    }

    public List<DeletionSedanOperation> deletions() {
        return operations.stream()
                .filter(DeletionSedanOperation.class::isInstance)
                .map(DeletionSedanOperation.class::cast)
                .toList();
    }

    public List<UpdateSedanOperation> updates() {
        return operations.stream()
                .filter(UpdateSedanOperation.class::isInstance)
                .map(UpdateSedanOperation.class::cast)
                .toList();
    }

    public List<MoveSedanOperation> moves() {
        return operations.stream()
                .filter(MoveSedanOperation.class::isInstance)
                .map(MoveSedanOperation.class::cast)
                .toList();
    }

    public boolean isEmpty() {
        return operations.isEmpty();
    }

    public boolean areEqual() {
        return operations.isEmpty();
    }
}
