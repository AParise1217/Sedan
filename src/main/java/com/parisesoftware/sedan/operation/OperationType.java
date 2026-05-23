package com.parisesoftware.sedan.operation;

/**
 * Types of Operations that Sedan supports
 */
public enum OperationType {

    ADD("add"), DELETE("delete"), UPDATE("update");

    /**
     * The Operation Name to be Written to the Output
     */
    private final String name;

    OperationType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
