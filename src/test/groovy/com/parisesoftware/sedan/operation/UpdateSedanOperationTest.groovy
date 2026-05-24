package com.parisesoftware.sedan.operation

import spock.lang.Specification

class UpdateSedanOperationTest extends Specification {

    def "should implement SedanOperation"() {
        expect:
        new UpdateSedanOperation('key', 'val') instanceof SedanOperation
    }

    def "type() should return UPDATE"() {
        expect:
        new UpdateSedanOperation('key', 'val').type() == OperationType.UPDATE
    }

    def "name() should return the name passed to the constructor"() {
        expect:
        new UpdateSedanOperation('myKey', 'val').name() == 'myKey'
    }

    def "value() should return the value passed to the constructor"() {
        expect:
        new UpdateSedanOperation('key', 'newValue').value() == 'newValue'
    }

    def "should throw NullPointerException when name is null"() {
        when:
        new UpdateSedanOperation(null, 'val')

        then:
        thrown(NullPointerException)
    }
}
