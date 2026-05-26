package com.parisesoftware.sedan.operation

import spock.lang.Specification

class DeletionSedanOperationTest extends Specification {

    def "should implement SedanOperation"() {
        expect:
        new DeletionSedanOperation('key') instanceof SedanOperation
    }

    def "type() should return DELETE"() {
        expect:
        new DeletionSedanOperation('key').type() == OperationType.DELETE
    }

    def "name() should return the name passed to the constructor"() {
        expect:
        new DeletionSedanOperation('myKey').name() == 'myKey'
    }

    def "value() should always return null"() {
        expect:
        new DeletionSedanOperation('key').value() == null
    }

    def "should throw NullPointerException when name is null"() {
        when:
        new DeletionSedanOperation(null)

        then:
        thrown(NullPointerException)
    }
}
