package com.parisesoftware.sedan

/**
 * Types of Operations that Sedan supports
 */
enum OperationType {

    ADD('add'), DELETE('delete'), UPDATE('update')

    /**
     * The Operation Name to be Written to the Output
     */
    private final String name

    private OperationType(String name) {
        this.name = name
    }

    @Override
    String toString() {
        return this.name
    }
}
