package com.parisesoftware.sedan.operation

import spock.lang.Specification

class AdditionSedanOperationTest extends Specification {

    def "should implement SedanOperation"() {
        expect:
        new AdditionSedanOperation('key', 'val') instanceof SedanOperation
    }

    def "type() should return ADD"() {
        expect:
        new AdditionSedanOperation('key', 'val').type() == OperationType.ADD
    }

    def "name() should return the name passed to the constructor"() {
        expect:
        new AdditionSedanOperation('myKey', 'val').name() == 'myKey'
    }

    def "value() should return the value passed to the constructor"() {
        expect:
        new AdditionSedanOperation('key', 'myValue').value() == 'myValue'
    }

    def "should throw NullPointerException when name is null"() {
        when:
        new AdditionSedanOperation(null, 'val')

        then:
        thrown(NullPointerException)
    }
}
